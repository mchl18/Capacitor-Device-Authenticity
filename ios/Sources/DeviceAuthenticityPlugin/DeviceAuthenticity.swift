import Foundation
import Capacitor


struct CheckPathsResult {
    let hasOffendingPaths: Bool
    let detectedForbiddenPaths: [String]
}
struct CheckAppstoresResult {
    let hasThirdPartyAppStore: Bool
    let forbiddenAppStoreSchemas: [String]
}
struct CheckPrivateWriteResult {
    let canWritePrivate: Bool
    let detectedPrivateWritePaths: [String]
}

struct CheckJailbreakResult {
    let isJailbroken: Bool
    let canWritePrivate: Bool;
    let hasOffendingPaths: Bool;
    let detectedForbiddenPaths: [String]
    let forbiddenAppStoreSchemas: [String]
    let detectedPrivateWritePaths: [String]
}

class DeviceAuthenticity {
    func checkAuthenticity(_ call: CAPPluginCall) {
        let jailbreakIndicatorPaths = call.getArray("jailbreakIndicatorPaths", String.self) ?? []
        let forbiddenAppStoreSchemas = call.getArray("forbiddenAppStoreSchemas", String.self) ?? []
        let hasPaths: CheckPathsResult = _checkOffendingPaths(jailbreakIndicatorPaths: jailbreakIndicatorPaths)
        let canWritePrivate: CheckPrivateWriteResult = _checkPrivateWrite()
        let isEmulator = _isRunningOnSimulator()
        let hasThirdPartyAppStore: CheckAppstoresResult = _hasThirdPartyAppStore(forbiddenAppStoreSchemas: forbiddenAppStoreSchemas)
        var failedChecks: [String] = [];
        let isJailbroken = isEmulator || canWritePrivate.canWritePrivate || hasPaths.hasOffendingPaths || hasThirdPartyAppStore.hasThirdPartyAppStore
        if isJailbroken {
            failedChecks.append("isJailbroken")
        }
        if isEmulator {
            failedChecks.append("isEmulator")
        }
        if canWritePrivate.canWritePrivate {
            failedChecks.append("canWritePrivate")
        }
        if hasPaths.hasOffendingPaths {
            failedChecks.append("hasOffendingPaths")
        }
        if hasThirdPartyAppStore.hasThirdPartyAppStore {
            failedChecks.append("hasThirdPartyAppStore")
        }

        call.resolve([
            "isJailbroken": isJailbroken,
            "isEmulator": isEmulator,
            "hasThirdPartyAppStore": hasThirdPartyAppStore.hasThirdPartyAppStore,
            "detectedThirdPartyAppStoreSchemas": hasThirdPartyAppStore.forbiddenAppStoreSchemas,
            "canWritePrivate": canWritePrivate.canWritePrivate,
            "detectedPrivateWritePaths": canWritePrivate.detectedPrivateWritePaths,
            "hasOffendingPaths": hasPaths.hasOffendingPaths,
            "detectedForbiddenPaths": hasPaths.detectedForbiddenPaths,
            "failedChecks": failedChecks
        ])
    }
    
    func isEmulator(_ call: CAPPluginCall) {
        let isEmulator = _isRunningOnSimulator()
        
        call.resolve(["isEmulator": isEmulator])
    }

    func isJailbroken(_ call: CAPPluginCall) {
        let jailbreakIndicatorPaths = call.getArray("jailbreakIndicatorPaths", String.self) ?? []
        let forbiddenAppStoreSchemas = call.getArray("forbiddenAppStoreSchemas", String.self) ?? []
        let isJailbroken: CheckJailbreakResult = _checkIsJailbroken(jailbreakIndicatorPaths: jailbreakIndicatorPaths, forbiddenAppStoreSchemas: forbiddenAppStoreSchemas)
        
        call.resolve(["isJailbroken": isJailbroken.isJailbroken, 
                    "detectedForbiddenPaths": isJailbroken.detectedForbiddenPaths, 
                    "canWritePrivate": isJailbroken.canWritePrivate,
                    "detectedPrivateWritePaths": isJailbroken.detectedPrivateWritePaths,
                    "detectedThirdPartyAppStoreSchemas": isJailbroken.forbiddenAppStoreSchemas])
    }

    func checkPaths(_ call: CAPPluginCall) {
        let jailbreakIndicatorPaths = call.getArray("jailbreakIndicatorPaths", String.self) ?? []
        let hasPaths = _checkOffendingPaths(jailbreakIndicatorPaths: jailbreakIndicatorPaths)
        
        call.resolve(["hasPaths": hasPaths])
    }
    
    func checkPrivateWrite(_ call: CAPPluginCall) {
        let hasPaths = _checkPrivateWrite()
        
        call.resolve(["canWritePrivate": hasPaths])
    }

    func hasThirdPartyAppStore(_ call: CAPPluginCall) {
        let forbiddenAppStoreSchemas = call.getArray("forbiddenAppStoreSchemas", String.self) ?? []
        let hasThirdPartyAppStore = _hasThirdPartyAppStore(forbiddenAppStoreSchemas: forbiddenAppStoreSchemas)
        
        call.resolve(["hasThirdPartyAppStore": hasThirdPartyAppStore])
    }

    func isRooted(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }

    func isInstalledFromAllowedStore(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }

    func getApkCertSignature(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }

    func checkApkCertSignature(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }

    func checkTags(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }
    
    private func _checkIsJailbroken(jailbreakIndicatorPaths: [String] = [],forbiddenAppStoreSchemas: [String] = []) -> CheckJailbreakResult {
        let checkPathsResult = _checkOffendingPaths(jailbreakIndicatorPaths: jailbreakIndicatorPaths)
        let hasThirdPartyAppStore = _hasThirdPartyAppStore(forbiddenAppStoreSchemas: forbiddenAppStoreSchemas)
        let canWritePrivate = _checkPrivateWrite()
        let isJailbroken = checkPathsResult.hasOffendingPaths || 
            hasThirdPartyAppStore.hasThirdPartyAppStore || 
            canWritePrivate.canWritePrivate;

        return CheckJailbreakResult(isJailbroken: isJailbroken,
            canWritePrivate: canWritePrivate.canWritePrivate,
            hasOffendingPaths: checkPathsResult.hasOffendingPaths,
            detectedForbiddenPaths: checkPathsResult.detectedForbiddenPaths,
            forbiddenAppStoreSchemas: hasThirdPartyAppStore.forbiddenAppStoreSchemas,
            detectedPrivateWritePaths: canWritePrivate.detectedPrivateWritePaths)
    }
    
    private func _checkOffendingPaths(jailbreakIndicatorPaths: [String]) -> CheckPathsResult {
        let fileManager = FileManager.default
        let jailbreakPaths = [
            "/Applications/Cydia.app",
            "/Applications/Sileo.app",
            "/Applications/Zebra.app",
            "/Applications/Installer.app",
            "/Applications/Unc0ver.app",
            "/Applications/Checkra1n.app",
            "/Library/MobileSubstrate/MobileSubstrate.dylib",
            "/usr/sbin/sshd",
            "/usr/bin/sshd",
            "/usr/libexec/sftp-server",
            "/etc/apt",
            "/private/var/lib/apt/",
            "/private/var/mobile/Library/Cydia/",
            "/private/var/stash",
            "/private/var/db/stash",
            "/private/var/jailbreak",
            "/var/mobile/Library/SBSettings/Themes"
        ]
        var paths: [String] = [];
        let pathsToCheck = jailbreakIndicatorPaths.count > 0 ? jailbreakIndicatorPaths : jailbreakPaths


        for path in pathsToCheck {
            if fileManager.fileExists(atPath: path) {
                paths.append(path)
            }
        }
        return CheckPathsResult(hasOffendingPaths: !paths.isEmpty, detectedForbiddenPaths: paths)
    }
    
    private func _checkPrivateWrite() -> CheckPrivateWriteResult {
        let fileManager = FileManager.default
        do {
            try "jailbreak test".write(toFile: "/private/jailbreak.txt", atomically: true, encoding: .utf8)
            try fileManager.removeItem(atPath: "/private/jailbreak.txt")
            return CheckPrivateWriteResult(canWritePrivate: true, detectedPrivateWritePaths: ["/private"])
        } catch {
            return CheckPrivateWriteResult(canWritePrivate: false, detectedPrivateWritePaths: [])
        }
    }
    
    private func _hasThirdPartyAppStore(forbiddenAppStoreSchemas: [String] = []) -> CheckAppstoresResult {
        let forbiddenAppStoreSchemasDefault = [
            "cydia://",
            "sileo://",
            "zbra://",
            "filza://",
            "undecimus://",
            "activator://"
        ]
        
        let schemasToCheck = forbiddenAppStoreSchemas.count > 0 ? forbiddenAppStoreSchemas : forbiddenAppStoreSchemasDefault
        var foundSchemas: [String] = []
        
        for schema in schemasToCheck {
            if let url = URL(string: schema) {
                if UIApplication.shared.canOpenURL(url) {
                    foundSchemas.append(schema)
                }
            }
        }
        return CheckAppstoresResult(hasThirdPartyAppStore: !forbiddenAppStoreSchemas.isEmpty, forbiddenAppStoreSchemas: foundSchemas)
    }

    private func _isRunningOnSimulator() -> Bool {
        #if arch(i386) || arch(x86_64)
            return false
        #elseif targetEnvironment(simulator)
            return true
        #else
            return false
        #endif
    }
}
