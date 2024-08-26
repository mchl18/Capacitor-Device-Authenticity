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
    
    private func checkIsJailbroken() -> Bool {
        return checkPaths() || checkPrivateWrite() || checkCydia()
    }
    
    private func checkPaths() -> Bool {
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
    
    private func checkPrivateWrite() -> Bool {
        let fileManager = FileManager.default
        do {
            try "jailbreak test".write(toFile: "/private/jailbreak.txt", atomically: true, encoding: .utf8)
            try fileManager.removeItem(atPath: "/private/jailbreak.txt")
            return true
        } catch {
            return false
        }
    }
    
    private func checkCydia() -> Bool {
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