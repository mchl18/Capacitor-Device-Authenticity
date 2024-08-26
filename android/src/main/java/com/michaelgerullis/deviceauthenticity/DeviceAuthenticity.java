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
            ret.put("isRooted", checkIsRooted());
            ret.put("isEmulator", isEmulator() || isRunningInEmulator());
            String apkSignature = getApkSignature();
            ret.put("apkSignatureMatch", checkApkCertSignature(expectedApkSignature));
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

            ret.put("isInstalledFromAllowedStore", isInstalledFromAllowedStore(allowedStores));
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Error checking device authenticity: " + e.getMessage());
        }
    }

    private String getApkSignature() throws PackageManager.NameNotFoundException, NoSuchAlgorithmException {
        PackageInfo packageInfo;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), PackageManager.GET_SIGNING_CERTIFICATES);
            Signature[] signatures = packageInfo.signingInfo.getApkContentsSigners();
            return calculateSignature(signatures[0]);
        } else {
            packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            return calculateSignature(signatures[0]);
        }
    }

    private String checkApkCertSignature(String expectedApkSignature) {
        try {
            String apkSignature = getApkSignature();
            String parsedExpectedApkSignature = expectedApkSignature.replace(":", "").toLowerCase();
            String parsedApkSignature = apkSignature.replace(":", "").toLowerCase();
            boolean isValid = apkSignature.equals(parsedExpectedApkSignature);
            return String.valueOf(isValid);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
    }

    private String calculateSignature(Signature sig) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(sig.toByteArray());
        byte[] digest = md.digest();
        return Base64.encodeToString(digest, Base64.DEFAULT);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xFF & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    private boolean isRunningInEmulator() {
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

    private boolean checkIsRooted() {
        return checkTag() || checkPaths() || checkExecutableFiles();
    }

    private boolean checkTag() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private boolean checkPaths() {
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

    private boolean checkExecutableFiles() {
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
                if (executeCommand(fullCommand)) {
                    return true;
                }
            }
            if (executeCommand(executableFile)) {
                return true;
            }
        }
        return false;
    }

    private boolean executeCommand(String command) {
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

    private boolean isInstalledFromAllowedStore(List<String> allowedStores) {
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