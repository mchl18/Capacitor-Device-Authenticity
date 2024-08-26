package com.michaelgerullis.deviceauthenticity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "DeviceAuthenticity")
public class DeviceAuthenticityPlugin extends Plugin {

    private DeviceAuthenticity implementation;

    @Override
    public void load() {
        implementation = new DeviceAuthenticity();
    }

    @PluginMethod
    public void checkAuthenticity(PluginCall call) {
        implementation.checkAuthenticity(call);
    }

    @PluginMethod
    public void isEmulator(PluginCall call) {
        implementation.isEmulator(call);
    }

    @PluginMethod
    public void isRooted(PluginCall call) {
        implementation.isRooted(call);
    }

    @PluginMethod
    public void isInstalledFromAllowedStore(PluginCall call) {
        implementation.checkAuthenticity(call);
    }
    
    @PluginMethod
    public void getApkSignature(PluginCall call) {
        implementation.getApkSignature(call);
    }

    @PluginMethod
    public void getApkCertSignature(PluginCall call) {
        implementation.getApkCertSignature(call);
    }

    @PluginMethod
    public void checkApkCertSignature(PluginCall call) {
        implementation.checkApkCertSignature(call);
    }

    @PluginMethod
    public void checkTags(PluginCall call) {
        implementation.checkTags(call);
    }
}
