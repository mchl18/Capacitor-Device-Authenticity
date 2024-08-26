import Foundation
import Capacitor

@objc(DeviceAuthenticityPlugin)
public class DeviceAuthenticityPlugin: CAPPlugin {
    @objc func checkAuthenticity(_ call: CAPPluginCall) {
        let isJailbroken = checkIsJailbroken()
        let isEmulator = isRunningOnSimulator()
        
        call.resolve([
            "isJailbroken": isJailbroken,
            "isEmulator": isEmulator
        ])
    }
    
    @objc func isEmulator(_ call: CAPPluginCall) {
        let isEmulator = isRunningOnSimulator()
        
        call.resolve(["isEmulator": isEmulator])
    }

    @objc func isJailbroken(_ call: CAPPluginCall) {
        let isJailbroken = checkIsJailbroken()
        
        call.resolve(["isJailbroken": isJailbroken])
    }

    @objc func checkPaths(_ call: CAPPluginCall) {
        let hasPaths = _checkPaths()
        
        call.resolve(["hasPaths": hasPaths])
    }
    
    @objc func checkPrivateWrite(_ call: CAPPluginCall) {
        let hasPaths = _checkPrivateWrite()
        
        call.resolve(["canWritePrivate": hasPaths])
    }

    @objc func hasThirdPartyAppStore(_ call: CAPPluginCall) {
        let hasThirdPartAppStore = _hasThirdPartAppStore()
        
        call.resolve(["hasThirdPartAppStore": hasThirdPartAppStore])
    }
    
    private func checkIsJailbroken() -> Bool {
        return _checkPaths() || _checkPrivateWrite() || _hasThirdPartAppStore() || _checkFork()
    }
    
    private func _checkPaths() -> Bool {
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
        
        for path in jailbreakPaths {
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
    
    private func _hasThirdPartAppStore() -> Bool {
       let jailbreakSchemes = [
            "cydia://",
            "sileo://",
            "zbra://",
            "filza://",
            "undecimus://",
            "activator://"
        ]
        
        for scheme in jailbreakSchemes {
            if let url = URL(string: scheme) {
                if UIApplication.shared.canOpenURL(url) {
                    return true
                }
            }
        }
        return false
    }

    private func isRunningOnSimulator() -> Bool {
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