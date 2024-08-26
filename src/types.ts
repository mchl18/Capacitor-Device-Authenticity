export type DeviceAuthenticityResult = {
  // Android only
  isRooted?: boolean;
  // Both Android and iOS
  isEmulator?: boolean;
  // Android only
  isInstalledFromAllowedStore?: boolean;
  // iOS only
  isJailbroken?: boolean;
  // Android only
  // If it is a string, it is the expected to be an error message.
  apkSignatureMatch?: boolean;
  // iOS only
  hasPaths?: boolean;
  // iOS only
  canWritePrivate?: boolean;
  // iOS only
  hasThirdPartyAppStore?: boolean;
  error?: string;
};

export type DeviceAuthenticityOptions = {
  // Android only
  allowedStores?: string[];
  // Android only
  apkSignature?: string;
  // Android only
  // Override for the default root indicator tags which are:
  // "test-keys",           // Common for many rooted devices
  // "dev-keys",            // Development keys, often seen in custom ROMs
  // "userdebug",           // User-debuggable build, common in rooted devices
  // "engineering",         // Engineering build, may indicate a modified system
  // "release-keys-debug",  // Debug version of release keys
  // "custom",              // Explicitly marked as custom
  // "rooted",              // Explicitly marked as rooted (rare, but possible)
  // "supersu",             // Indicates SuperSU rooting tool
  // "magisk",              // Indicates Magisk rooting framework
  // "lineage",             // LineageOS custom ROM
  // "unofficial"           // Unofficial build, common in custom ROMs

  // If you are planning to extend the list, please do as follow:
  // const completeList = [...DeviceAuthenticityWeb.DEFAULT_ANDROID_ROOT_INDICATOR_TAGS, ...yourList];
  // Then use completeList in the plugin. Otherwise, the default list will be used.
  androidRootIndicatorTags?: string[];
  // iOS only
  // Override for the default jailbreak paths which are:
  // "/Applications/Cydia.app",
  // "/Library/MobileSubstrate/MobileSubstrate.dylib",
  // "/bin/bash",
  // "/usr/sbin/sshd",
  // "/etc/apt",
  // "/private/var/lib/apt/"
  // If you are planning to extend the list, please do as follow:
  // const completeList = [...DeviceAuthenticityWeb.DEFAULT_IOS_JAILBREAK_PATHS, ...yourList];
  // Then use completeList in the plugin. Otherwise, the default list will be used.
  iosJailbreakPaths?: string[];
  // Android only
  // Override for the default rooted paths which are:
  // "/system/app/Superuser.apk",
  // "/sbin/su",
  // "/system/bin/su",
  // "/system/xbin/su",
  // "/data/local/xbin/su",
  // "/data/local/bin/su",
  // "/system/sd/xbin/su",
  // "/system/bin/failsafe/su",
  // "/data/local/su",
  // "/su/bin/su"
  // If you are planning to extend the list, please do as follow:
  // const completeList = [...DeviceAuthenticityWeb.DEFAULT_ANDROID_ROOTED_PATHS, ...yourList];
  // Then use completeList in the plugin. Otherwise, the default list will be used.
  androidRootedPaths?: string[];
};

export type DeviceAuthenticityError = {
  error: string;
};
