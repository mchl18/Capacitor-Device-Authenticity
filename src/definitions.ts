import {
  DeviceAuthenticityError,
  DeviceAuthenticityOptions,
  DeviceAuthenticityResult,
  DeviceAuthenticityJailbreakOptions,
  DeviceAuthenticityRootedOptions,
  DeviceAuthenticityInstalledFromAllowedStoreOptions,
  DeviceAuthenticityCheckApkCertSignatureOptions,
  DeviceAuthenticityCheckTagsOptions,
  DeviceAuthenticityCheckPathsOptions,
  DeviceAuthenticityCheckExecutableFilesOptions,
} from './types';

export interface DeviceAuthenticityPlugin {
  checkAuthenticity(
    options?: DeviceAuthenticityOptions,
  ): Promise<DeviceAuthenticityResult | DeviceAuthenticityError>;

  isEmulator(): Promise<{ isEmulator: boolean } | DeviceAuthenticityError>;
  // iOS only
  isJailbroken(
    options?: DeviceAuthenticityJailbreakOptions,
  ): Promise<{ isJailbroken: boolean } | DeviceAuthenticityError>;
  // Android only
  isRooted(
    options?: DeviceAuthenticityRootedOptions,
  ): Promise<{ isRooted: boolean } | DeviceAuthenticityError>;
  // Android only
  isNotInstalledFromAllowedStore(
    options?: DeviceAuthenticityInstalledFromAllowedStoreOptions,
  ): Promise<
    { isNotInstalledFromAllowedStore: boolean } | DeviceAuthenticityError
  >;
  // Android only
  getApkCertSignature(): Promise<
    { apkCertSignature: string } | DeviceAuthenticityError
  >;
  // Android only
  checkApkCertSignature(
    options?: DeviceAuthenticityCheckApkCertSignatureOptions,
  ): Promise<{ apkCertSignatureMatches: boolean } | DeviceAuthenticityError>;
  // Android only
  checkTags(
    options?: DeviceAuthenticityCheckTagsOptions,
  ): Promise<{ hasOffendingTags: boolean } | DeviceAuthenticityError>;
  checkPaths(
    options?: DeviceAuthenticityCheckPathsOptions,
  ): Promise<{ hasOffendingPaths: boolean } | DeviceAuthenticityError>;
  // Android only
  checkExecutableFiles(
    options?: DeviceAuthenticityCheckExecutableFilesOptions,
  ): Promise<
    { hasOffendingExecutableFiles: boolean } | DeviceAuthenticityError
  >;
  // iOS only
  checkPrivateWrite(): Promise<
    { canWritePrivate: boolean } | DeviceAuthenticityError
  >;
  // iOS only
  hasThirdPartyAppStore(): Promise<
    { hasThirdPartyAppStore: boolean } | DeviceAuthenticityError
  >;
  isValid(value: unknown): value is boolean | string;
  isError(value: unknown): value is DeviceAuthenticityError;
}
