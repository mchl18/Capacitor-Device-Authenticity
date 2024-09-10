package com.michaelgerullis.deviceauthenticity;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
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

import org.json.JSONException;

public class DeviceAuthenticity {

    private static final String DEFAULT_ALLOWED_STORE = "com.android.vending";
    private static final String[] DEFAULT_FORBIDDEN_TAGS = new String[] {
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

    private static final String[] DEFAULT_FORBIDDEN_PATHS = new String[] {
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

    private static final String[] DEFAULT_FORBIDDEN_EXECUTABLES = new String[] {
            "su",
            "/system/xbin/su",
            "/system/bin/su",
            "busybox"
    };

    private Context context;

    public DeviceAuthenticity(Context context) {
        this.context = context;
    }

    public void checkAuthenticity(PluginCall call) {
        try {
            String expectedApkSignature = call.getString("apkCertSignature");
            JSObject ret = new JSObject();
            JSArray rootIndicatorTagsArray = call.getArray("rootIndicatorTags");
            JSArray rootIndicatorPathsArray = call.getArray("rootIndicatorPaths");
            JSArray rootIndicatorFilesArray = call.getArray("rootIndicatorFiles");
            String apkSignature = _getApkCertSignature();

            // Get the allowed app stores from the call, or use default
            JSArray allowedStoresArray = call.getArray("allowedStores");
            List<String> allowedStores = _getAllowedStores(allowedStoresArray);

            JSArray failedChecks = new JSArray();

            boolean isRooted = _checkIsRooted(rootIndicatorTagsArray, rootIndicatorPathsArray, rootIndicatorFilesArray);
            ret.put("isRooted", isRooted);
            if (isRooted) failedChecks.put("Device is rooted");

            boolean isEmulator = _isEmulator() || _isRunningInEmulator();
            ret.put("isEmulator", isEmulator);
            if (isEmulator) failedChecks.put("Device is an emulator");

            if (expectedApkSignature != null && !expectedApkSignature.isEmpty()) {
                Boolean signatureMatch = _checkApkCertSignature(expectedApkSignature);
                ret.put("apkCertSignatureMatch", signatureMatch);
                if (!signatureMatch) failedChecks.put("APK signature mismatch");
            }

            if (apkSignature != null && !apkSignature.isEmpty()) {
                ret.put("apkCertSignature", apkSignature);
            }

            boolean hasOffendingPaths = _checkPaths(rootIndicatorPathsArray);
            ret.put("hasOffendingPaths", hasOffendingPaths);
            if (hasOffendingPaths) failedChecks.put("hasOffendingPaths");

            boolean isNotInstalledFromAllowedStore = _isNotInstalledFromAllowedStore(allowedStores);
            ret.put("isNotInstalledFromAllowedStore", isNotInstalledFromAllowedStore);
            if (isNotInstalledFromAllowedStore) failedChecks.put("isNotInstalledFromAllowedStore");

            boolean hasOffendingTags = _checkTags(rootIndicatorTagsArray);
            ret.put("hasOffendingTags", hasOffendingTags);
            if (hasOffendingTags) failedChecks.put("hasOffendingTags");

            boolean hasOffendingExecutableFiles = _checkExecutableFiles(rootIndicatorFilesArray);
            ret.put("hasOffendingExecutableFiles", hasOffendingExecutableFiles);
            if (hasOffendingExecutableFiles) failedChecks.put("hasOffendingExecutableFiles");

            ret.put("failedChecks", failedChecks);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking device authenticity: " + e.getMessage());
        }
    }

    public void isRooted(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            JSArray rootIndicatorTagsArray = call.getArray("rootIndicatorTags");
            JSArray rootIndicatorPathsArray = call.getArray("rootIndicatorPaths");
            JSArray rootIndicatorFilesArray = call.getArray("rootIndicatorFiles");
            ret.put("isRooted",
                    _checkIsRooted(rootIndicatorTagsArray, rootIndicatorPathsArray, rootIndicatorFilesArray));
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking device rooted status: " + e.getMessage());
        }
    }

    public void isEmulator(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            ret.put("isEmulator", _isEmulator() || _isRunningInEmulator());
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking device emulator status: " + e.getMessage());
        }
    }

    public void isNotInstalledFromAllowedStore(PluginCall call) {
        try {
            // Get the allowed app stores from the call, or use default
            JSArray allowedStoresArray = call.getArray("allowedStores");
            List<String> allowedStores = _getAllowedStores(allowedStoresArray);
            JSObject ret = new JSObject();
            ret.put("isNotInstalledFromAllowedStore", _isNotInstalledFromAllowedStore(allowedStores));
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking device emulator status: " + e.getMessage());
        }
    }

    public void getApkCertSignature(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            ret.put("apkCertSignature", _getApkCertSignature());
            call.resolve(ret);
        } catch (Exception e) {
            e.printStackTrace();
            call.reject("Error getting APK certificate signature: " + e.getMessage());
        }
    }

    public void checkApkCertSignature(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            String expectedApkSignature = call.getString("expectedApkSignature");
            ret.put("apkCertSignatureMatches", _checkApkCertSignature(expectedApkSignature));
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking APK certificate signature: " + e.getMessage());
        }
    }

    public void checkTags(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            JSArray rootIndicatorTagsArray = call.getArray("rootIndicatorTags");
            ret.put("hasOffendingTags", _checkTags(rootIndicatorTagsArray));
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking build tags: " + e.getMessage());
        }
    }

    public void checkPaths(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            JSArray rootIndicatorPathsArray = call.getArray("rootIndicatorPaths");
            ret.put("hasOffendingPaths", _checkPaths(rootIndicatorPathsArray));
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking build paths: " + e.getMessage());
        }
    }

    public void checkExecutableFiles(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            JSArray rootIndicatorFilesArray = call.getArray("rootIndicatorFiles");
            ret.put("hasOffendingExecutableFiles", _checkExecutableFiles(rootIndicatorFilesArray));
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking executable files: " + e.getMessage());
        }
    }

    public void isJailbroken(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("error", "Not implemented on Android");
        call.resolve(ret);
    }

    public void checkPrivateWrite(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("error", "Not implemented on Android");
        call.resolve(ret);
    }

    public void hasThirdPartyAppStore(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("error", "Not implemented on Android");
        call.resolve(ret);
    }

    private String _getApkCertSignature() throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        PackageInfo packageInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNING_CERTIFICATES);
            Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();
            return _calculateSignature(signatures[0]);
        } else {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            return _calculateSignature(signatures[0]);
        }
    }

    private Boolean _checkApkCertSignature(String expectedApCertkSignature) {
        try {
            String apkCertSignature = _getApkCertSignature();
            if (expectedApCertkSignature == null || expectedApCertkSignature.isEmpty()) {
                return true;
            }
            String parsedExpectedApkSignature = expectedApCertkSignature.replace(":", "").toLowerCase();
            String parsedApkSignature = apkCertSignature.replace(":", "").toLowerCase();
            boolean isValid = parsedApkSignature.equals(parsedExpectedApkSignature);
            return isValid;
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String _calculateSignature(Signature sig) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(sig.toByteArray());
        byte[] digest = md.digest();
        String signature = Base64.encodeToString(digest, Base64.DEFAULT);
        return signature.replace(":", "").toLowerCase();
    }

    // @TODO: add ability to pass extra emulator checks
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

    // @TODO: add ability to pass extra emulator checks
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

    private boolean _checkIsRooted(JSArray rootIndicatorTagsArray, JSArray rootIndicatorPathsArray,
            JSArray rootIndicatorFilesArray) {
        try {
            return _checkTags(rootIndicatorTagsArray)
                    || _checkPaths(rootIndicatorPathsArray)
                    || _checkExecutableFiles(rootIndicatorFilesArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean _checkTags(JSArray rootIndicatorTagsArray) throws JSONException {
        String buildTags = android.os.Build.TAGS;
        String[] tagsToCheck;

        if (buildTags == null || buildTags.isEmpty())
            return false;

        if (rootIndicatorTagsArray != null && rootIndicatorTagsArray.length() > 0) {
            tagsToCheck = new String[rootIndicatorTagsArray.length()];
            for (int i = 0; i < rootIndicatorTagsArray.length(); i++) {
                tagsToCheck[i] = rootIndicatorTagsArray.getString(i);
            }
        } else {
            tagsToCheck = DEFAULT_FORBIDDEN_TAGS;
        }

        for (String tag : tagsToCheck) {
            if (buildTags.toLowerCase().contains(tag.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    private boolean _checkPaths(JSArray rootIndicatorPathsArray) throws JSONException {
        String[] paths;

        if (rootIndicatorPathsArray != null && rootIndicatorPathsArray.length() > 0) {
            paths = new String[rootIndicatorPathsArray.length()];
            for (int i = 0; i < rootIndicatorPathsArray.length(); i++) {
                paths[i] = rootIndicatorPathsArray.getString(i);
            }
        } else {
            paths = DEFAULT_FORBIDDEN_PATHS;
        }
        for (String path : paths) {
            if (new File(path).exists())
                return true;
        }
        return false;
    }

    private boolean _checkExecutableFiles(JSArray rootIndicatorFilesArray) throws JSONException {
        ArrayList<String> executableFiles;

        if (rootIndicatorFilesArray != null && rootIndicatorFilesArray.length() > 0) {
            executableFiles = new ArrayList<>();
            for (int i = 0; i < rootIndicatorFilesArray.length(); i++) {
                executableFiles.add(rootIndicatorFilesArray.getString(i));
            }
        } else {
            executableFiles = new ArrayList<>(Arrays.asList(DEFAULT_FORBIDDEN_EXECUTABLES));
        }

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

    private boolean _isNotInstalledFromAllowedStore(List<String> allowedStores) {
        try {
            String installer = context.getPackageManager()
                    .getInstallerPackageName(context.getPackageName());
            if (installer == null) {
                return true;
            }
            if (allowedStores == null || allowedStores.isEmpty()) {
                return true;
            }
            for (String store : allowedStores) {
                if (installer.equals(store)) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<String> _getAllowedStores(JSArray allowedStoresArray) throws JSONException {
        List<String> allowedStores = new ArrayList<>();

        if (allowedStoresArray != null && allowedStoresArray.length() > 0) {
            for (int i = 0; i < allowedStoresArray.length(); i++) {
                allowedStores.add(allowedStoresArray.getString(i));
            }
        } else {
            allowedStores.add(DEFAULT_ALLOWED_STORE);
        }
        return allowedStores;
    }
}