import Foundation
import Capacitor

@objc(DeviceAuthenticityPlugin)
public class DeviceAuthenticityPlugin: CAPPlugin, CAPBridgedPlugin {
    public let identifier = "DeviceAuthenticityPlugin"
    public let jsName = "DeviceAuthenticity"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "checkAuthenticity", returnType: CAPPluginReturnPromise), 
        CAPPluginMethod(name: "isEmulator", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isJailbroken", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPaths", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPrivateWrite", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "hasThirdPartyAppStore", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isRooted", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isInstalledFromAllowedStore", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getApkCertSignature", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkApkCertSignature", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkTags", returnType: CAPPluginReturnPromise),
    ]
    private let implementation = DeviceAuthenticity()
    @objc func checkAuthenticity(_ call: CAPPluginCall) {
        implementation.checkAuthenticity(call)
    }
    
    @objc func isEmulator(_ call: CAPPluginCall) {
        implementation.isEmulator(call)
    }

    @objc func isJailbroken(_ call: CAPPluginCall) {
        implementation.isJailbroken(call)
    }

    @objc func checkPaths(_ call: CAPPluginCall) {
        implementation.checkPaths(call)
    }
    
    @objc func checkPrivateWrite(_ call: CAPPluginCall) {
        implementation.checkPrivateWrite(call)
    }

    @objc func hasThirdPartyAppStore(_ call: CAPPluginCall) {
        implementation.hasThirdPartyAppStore(call)
    }

    @objc func isRooted(_ call: CAPPluginCall) {
        implementation.isRooted(call)
    }

    @objc func isInstalledFromAllowedStore(_ call: CAPPluginCall) {
        implementation.isInstalledFromAllowedStore(call)
    }

    @objc func getApkCertSignature(_ call: CAPPluginCall) {
        implementation.getApkCertSignature(call)
    }

    @objc func checkApkCertSignature(_ call: CAPPluginCall) {
        implementation.checkApkCertSignature(call)
    }

    @objc func checkTags(_ call: CAPPluginCall) {
        implementation.checkTags(call)
    }
}
    