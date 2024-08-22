package com.michaelgerullis.deviceauthenticity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "DeviceAuthenticity")
public class DeviceAuthenticityPlugin extends Plugin {

    private DeviceAuthenticity implementation = new DeviceAuthenticity();

    @PluginMethod
    public void checkAuthenticity(PluginCall call) {
        implementation.checkAuthenticity(call);
    }
}
