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
  apkSignatureMatch?: boolean | string;
  
  error?: string;
};

export type DeviceAuthenticityOptions = {
  allowedStores?: string[];
  apkSignature?: string;
};
