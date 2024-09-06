package com.michaelgerullis.deviceauthenticity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import android.content.Context;

@CapacitorPlugin(name = "DeviceAuthenticity")
public class DeviceAuthenticityPlugin extends Plugin {

    private DeviceAuthenticity implementation;

    @Override
    public void load() {
        Context context = getContext();
        implementation = new DeviceAuthenticity(context);
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
        implementation.getApkCertSignature(call);
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

    @PluginMethod
    public void checkPaths(PluginCall call) {
        implementation.checkPaths(call);
    }

    @PluginMethod
    public void checkExecutableFiles(PluginCall call) {
        implementation.checkExecutableFiles(call);  
    }

    @PluginMethod
    public void isJailbroken(PluginCall call) {
        implementation.isJailbroken(call);
    }

    @PluginMethod
    public void checkPrivateWrite(PluginCall call) {
        implementation.checkPrivateWrite(call);
    }

    @PluginMethod
    public void hasThirdPartyAppStore(PluginCall call) {
        implementation.hasThirdPartyAppStore(call);
    }
}
