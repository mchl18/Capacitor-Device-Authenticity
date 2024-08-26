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
    
    @objc func hasCydia(_ call: CAPPluginCall) {
        let hasCydia = _checkCydia()
        
        call.resolve(["canWritePrivate": hasCydia])
    }
    
    private func checkIsJailbroken() -> Bool {
        return _checkPaths() || _checkPrivateWrite() || _checkCydia()
    }
    
    private func _checkPaths() -> Bool {
        let fileManager = FileManager.default
        let jailbreakPaths = [
            "/Applications/Cydia.app",
            "/Library/MobileSubstrate/MobileSubstrate.dylib",
            "/bin/bash",
            "/usr/sbin/sshd",
            "/etc/apt",
            "/private/var/lib/apt/"
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
    
    private func _checkCydia() -> Bool {
        if let url = URL(string: "cydia://package/com.example.package") {
            return UIApplication.shared.canOpenURL(url)
        }
        return false
    }

    private func isRunningOnSimulator() -> Bool {
        #if targetEnvironment(simulator)
            return true
        #else
            return false
        #endif
    }

}