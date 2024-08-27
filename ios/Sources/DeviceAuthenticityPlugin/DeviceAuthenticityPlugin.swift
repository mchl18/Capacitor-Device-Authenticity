import Foundation
import Capacitor

@objc(DeviceAuthenticityPlugin)
public class DeviceAuthenticityPlugin: CAPPlugin {
    @objc func checkAuthenticity(_ call: CAPPluginCall) {
        let jailbreakIndicatorPaths = call.getArray("jailbreakIndicatorPaths", String.self) ?? []
        let forbiddenSchemas = call.getArray("forbiddenSchemas", String.self) ?? []
        let isJailbroken = _checkIsJailbroken(jailbreakIndicatorPaths: jailbreakIndicatorPaths, forbiddenSchemas: forbiddenSchemas)
        let isEmulator = _isRunningOnSimulator()
        let hasThirdPartyAppStore = _hasThirdPartyAppStore()
        let canWritePrivate = _checkPrivateWrite()
        let hasPaths = _checkPaths(jailbreakIndicatorPaths: jailbreakIndicatorPaths)
        
        call.resolve([
            "isJailbroken": isJailbroken,
            "isEmulator": isEmulator,
            "hasThirdPartyAppStore": hasThirdPartyAppStore,
            "canWritePrivate": canWritePrivate,
            "hasPaths": hasPaths,
        ])
    }
    
    @objc func isEmulator(_ call: CAPPluginCall) {
        let isEmulator = _isRunningOnSimulator()
        
        call.resolve(["isEmulator": isEmulator])
    }

    @objc func isJailbroken(_ call: CAPPluginCall) {
        let jailbreakIndicatorPaths = call.getArray("jailbreakIndicatorPaths", String.self) ?? []
        let isJailbroken = _checkIsJailbroken(jailbreakIndicatorPaths: jailbreakIndicatorPaths)
        
        call.resolve(["isJailbroken": isJailbroken])
    }

    @objc func checkPaths(_ call: CAPPluginCall) {
        let jailbreakIndicatorPaths = call.getArray("jailbreakIndicatorPaths", String.self) ?? []
        let hasPaths = _checkPaths(jailbreakIndicatorPaths: jailbreakIndicatorPaths)
        
        call.resolve(["hasPaths": hasPaths])
    }
    
    @objc func checkPrivateWrite(_ call: CAPPluginCall) {
        let hasPaths = _checkPrivateWrite()
        
        call.resolve(["canWritePrivate": hasPaths])
    }

    @objc func hasThirdPartyAppStore(_ call: CAPPluginCall) {
        let hasThirdPartyAppStore = _hasThirdPartyAppStore()
        
        call.resolve(["hasThirdPartyAppStore": hasThirdPartyAppStore])
    }

    @objc func isRooted(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }

    @objc func isInstalledFromAllowedStore(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }

    @objc func getApkCertSignature(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }

    @objc func checkApkCertSignature(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }

    @objc func checkTags(_ call: CAPPluginCall) {
        call.resolve(["error": "Not implemented on iOS"])
    }
    
    private func _checkIsJailbroken(jailbreakIndicatorPaths: [String] = [],forbiddenSchemas: [String] = []) -> Bool {
        return _checkPaths(jailbreakIndicatorPaths: jailbreakIndicatorPaths) || _checkPrivateWrite() || _hasThirdPartyAppStore(forbiddenSchemas: forbiddenSchemas) || _checkFork()
    }
    
    private func _checkPaths(jailbreakIndicatorPaths: [String]) -> Bool {
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

        let pathsToCheck = jailbreakIndicatorPaths.count > 0 ? jailbreakIndicatorPaths : jailbreakPaths

        for path in pathsToCheck {
            if fileManager.fileExists(atPath: path) {
                return true
            }
        }
        return false
    }
    
    private func _checkPrivateWrite() -> Bool {
        let fileManager = FileManager.default
        do {
            try "jailbreak test".write(toFile: "/private/jailbreak.txt", atomically: true, encoding: .utf8)
            try fileManager.removeItem(atPath: "/private/jailbreak.txt")
            return true
        } catch {
            return false
        }
    }
    
    private func _hasThirdPartyAppStore(forbiddenSchemas: [String] = []) -> Bool {
        let jailbreakSchemas = [
            "cydia://",
            "sileo://",
            "zbra://",
            "filza://",
            "undecimus://",
            "activator://"
        ]
        
        let schemasToCheck = forbiddenSchemas.count > 0 ? forbiddenSchemas : jailbreakSchemas
        
        for scheme in schemasToCheck {
            if let url = URL(string: scheme) {
                if UIApplication.shared.canOpenURL(url) {
                    return true
                }
            }
        }
        return false
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

    private func _checkFork() -> Bool {
        let pointerToFork = UnsafeMutableRawPointer(bitPattern: -2)
        let forkPtr = dlsym(pointerToFork, "fork")
        return forkPtr != nil
    }

}
