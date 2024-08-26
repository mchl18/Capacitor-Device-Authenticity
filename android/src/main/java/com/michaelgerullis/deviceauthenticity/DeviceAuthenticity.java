package com.michaelgerullis.deviceauthenticity;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.JSArray;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.os.Build;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.PackageInfo;
import android.util.Base64;

@CapacitorPlugin(name = "DeviceAuthenticity")
public class DeviceAuthenticity extends Plugin {

    private static final String DEFAULT_ALLOWED_STORE = "com.android.vending";

    @PluginMethod
    public void checkAuthenticity(PluginCall call) {
        try {
            String expectedApkSignature = call.getString("apkSignature");
            String parsedExpectedApkSignature = expectedApkSignature.replace(":", "").toLowerCase();
            JSObject ret = new JSObject();
            ret.put("isRooted", _checkIsRooted());
            ret.put("isEmulator", _isEmulator() || _isRunningInEmulator());
            String apkSignature = _getApkCertSignature();
            ret.put("apkSignatureMatch", _checkApkCertSignature(expectedApkSignature));
            String parsedApkSignature = apkSignature.replace(":", "").toLowerCase();
            ret.put("apkSignature", parsedApkSignature);

            // Get the allowed app stores from the call, or use default
            JSArray allowedStoresArray = call.getArray("allowedStores");
            List<String> allowedStores = new ArrayList<>();
            if (allowedStoresArray != null && allowedStoresArray.length() > 0) {
                for (int i = 0; i < allowedStoresArray.length(); i++) {
                    allowedStores.add(allowedStoresArray.getString(i));
                }
            } else {
                allowedStores.add(DEFAULT_ALLOWED_STORE);
            }

            ret.put("isInstalledFromAllowedStore", _isInstalledFromAllowedStore(allowedStores));
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking device authenticity: " + e.getMessage());
        }
    }

    @PluginMethod
    public void isRooted(PluginCall call) {
        try {
            call.resolve(_checkIsRooted());
        } catch (Exception e) {
            call.reject("Error checking device rooted status: " + e.getMessage());
        }
    }

    @PluginMethod
    public void isEmulator(PluginCall call) {
        try {
            call.resolve(_isEmulator() || _isRunningInEmulator());
        } catch (Exception e) {
            call.reject("Error checking device emulator status: " + e.getMessage());
        }
    }

    @PluginMethod
    public void isInstalledFromAllowedStore(PluginCall call) {
        try {
            // Get the allowed app stores from the call, or use default
            JSArray allowedStoresArray = call.getArray("allowedStores");
            List<String> allowedStores = new ArrayList<>();
            if (allowedStoresArray != null && allowedStoresArray.length() > 0) {
                for (int i = 0; i < allowedStoresArray.length(); i++) {
                    allowedStores.add(allowedStoresArray.getString(i));
                }
            } else {
                allowedStores.add(DEFAULT_ALLOWED_STORE);
            }
            call.resolve(_isInstalledFromAllowedStore(allowedStores));
        } catch (Exception e) {
            call.reject("Error checking device emulator status: " + e.getMessage());
        }
    }

    @PluginMethod
    public void getApkCertSignature(PluginCall call) {
        try {
            call.resolve(_getApkCertSignature());
        } catch (Exception e) {
            e.printStackTrace();
            call.reject("Error getting APK certificate signature: " + e.getMessage());
        }
    }

    @PluginMethod
    public void checkApkCertSignature(PluginCall call) {
        try {
            call.resolve(_checkApkCertSignature(call.getString("expectedApkSignature")));
        } catch (Exception e) {
            call.reject("Error checking APK certificate signature: " + e.getMessage());
        }
    }

    @PluginMethod
    public void checkTags(PluginCall call) {
        try {
            call.resolve(_checkTag());
        } catch (Exception e) {
            call.reject("Error checking build tags: " + e.getMessage());
        }
    }

    @PluginMethod
    public void checkPaths(PluginCall call) {
        try {
            call.resolve(_checkPaths());
        } catch (Exception e) {
            call.reject("Error checking build paths: " + e.getMessage());
        }
    }

    @PluginMethod
    public void checkExecutableFiles(PluginCall call) {
        try {
            call.resolve(_checkExecutableFiles());
        } catch (Exception e) {
            call.reject("Error checking executable files: " + e.getMessage());
        }
    }

    @PluginMethod
    public void checkTags(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("error", "Not available on Android");
        call.resolve(ret);
    }

    @PluginMethod
    public void checkPaths(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("error", "Not available on Android");
        call.resolve(ret);
    }

    @PluginMethod
    public void checkExecutableFiles(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("error", "Not available on Android");
        call.resolve(ret);
    }

    private String _getApkCertSignature() throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        PackageInfo packageInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(),
                    PackageManager.GET_SIGNING_CERTIFICATES);
            Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();
            return _calculateSignature(signatures[0]);
        } else {
            packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(),
                    PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            return _calculateSignature(signatures[0]);
        }
    }

    private String _checkApkCertSignature(String expectedApkSignature) {
        try {
            String apkSignature = _getApkCertSignature();
            String parsedExpectedApkSignature = expectedApkSignature.replace(":", "").toLowerCase();
            String parsedApkSignature = apkSignature.replace(":", "").toLowerCase();
            boolean isValid = apkSignature.equals(parsedExpectedApkSignature);
            return String.valueOf(isValid);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    private String _calculateSignature(Signature sig) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(sig.toByteArray());
        byte[] digest = md.digest();
        return Base64.encodeToString(digest, Base64.DEFAULT);
    }

    private boolean _isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    private boolean _isRunningInEmulator() {
        boolean result = false;
        try {
            String buildDetails = Build.FINGERPRINT + Build.DEVICE + Build.MODEL + Build.BRAND + Build.PRODUCT
                    + Build.MANUFACTURER + Build.HARDWARE;
            result = buildDetails.toLowerCase().contains("generic")
                    || buildDetails.toLowerCase().contains("emulator")
                    || buildDetails.toLowerCase().contains("sdk");
        } catch (Exception e) {
            // Handle any exceptions
        }
        return result;
    }

    private boolean _checkIsRooted() {
        return _checkTag() || _checkPaths() || _checkExecutableFiles();
    }

    private boolean _checkTag() {
        String buildTags = android.os.Build.TAGS;
        if (buildTags == null)
            return false;

        String[] rootIndicatorTags = {
                "test-keys", // Common for many rooted devices
                "dev-keys", // Development keys, often seen in custom ROMs
                "userdebug", // User-debuggable build, common in rooted devices
                "engineering", // Engineering build, may indicate a modified system
                "release-keys-debug", // Debug version of release keys
                "custom", // Explicitly marked as custom
                "rooted", // Explicitly marked as rooted (rare, but possible)
                "supersu", // Indicates SuperSU rooting tool
                "magisk", // Indicates Magisk rooting framework
                "lineage", // LineageOS custom ROM
                "unofficial" // Unofficial build, common in custom ROMs
        };

        for (String tag : rootIndicatorTags) {
            if (buildTags.toLowerCase().contains(tag.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    private boolean _checkPaths() {
        String[] paths = {
                "/system/app/Superuser.apk",
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su",
                "/su/bin/su"
        };
        for (String path : paths) {
            if (new File(path).exists())
                return true;
        }
        return false;
    }

    private boolean _checkExecutableFiles() {
        ArrayList<String> executableFiles = new ArrayList<>(Arrays.asList(
                "su",
                "/system/xbin/su",
                "/system/bin/su",
                "busybox"));

        ArrayList<String> commands = new ArrayList<>(Arrays.asList(
                "which",
                "id",
                "ls /data"));

        for (String executableFile : executableFiles) {
            for (String command : commands) {
                String fullCommand = executableFile + " -c " + command;
                if (_executeCommand(fullCommand)) {
                    return true;
                }
            }
            if (_executeCommand(executableFile)) {
                return true;
            }
        }
        return false;
    }

    private boolean _executeCommand(String command) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            reader.close();
            return line != null;
        } catch (IOException e) {
            return false;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private boolean _isInstalledFromAllowedStore(List<String> allowedStores) {
        try {
            String installer = getContext().getPackageManager()
                    .getInstallerPackageName(getContext().getPackageName());
            if (installer == null) {
                return false;
            }
            for (String store : allowedStores) {
                if (installer.equals(store)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}