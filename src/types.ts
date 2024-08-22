export type DeviceAuthenticityResult = {
  // Android only
  isRooted?: boolean;
  // Both Android and iOS
  isEmulator?: boolean;
  // Android only
  isInstalledFromPlayStore?: boolean;
  // iOS only
  isJailbroken?: boolean;
  error?: string;
};

export type DeviceAuthenticityOptions = {
  allowedStores?: string[];
};
