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
  apkSignature?: string;
  
  error?: string;
};

export type DeviceAuthenticityOptions = {
  allowedStores?: string[];
};
