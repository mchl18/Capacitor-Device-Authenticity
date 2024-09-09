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
  ): Promise<DeviceAuthenticityResult>;

  isEmulator(): Promise<{ isEmulator: boolean } | { error: string }>;
  // iOS only
  isJailbroken(
    options?: DeviceAuthenticityJailbreakOptions,
  ): Promise<{ isJailbroken: boolean } | { error: string }>;
  // Android only
  isRooted(
    options?: DeviceAuthenticityRootedOptions,
  ): Promise<{ isRooted: boolean } | { error: string }>;
  // Android only
  isInstalledFromAllowedStore(
    options?: DeviceAuthenticityInstalledFromAllowedStoreOptions,
  ): Promise<{ isNotInstalledFromAllowedStore: boolean } | { error: string }>;
  // Android only
  getApkCertSignature(): Promise<
    { apkCertSignature: string } | { error: string }
  >;
  // Android only
  checkApkCertSignature(
    options?: DeviceAuthenticityCheckApkCertSignatureOptions,
  ): Promise<{ apkCertSignatureMatches: boolean } | { error: string }>;
  // Android only
  checkTags(
    options?: DeviceAuthenticityCheckTagsOptions,
  ): Promise<{ hasOffendingTags: boolean } | { error: string }>;
  checkPaths(
    options?: DeviceAuthenticityCheckPathsOptions,
  ): Promise<{ hasOffendingPaths: boolean } | { error: string }>;
  // Android only
  checkExecutableFiles(
    options?: DeviceAuthenticityCheckExecutableFilesOptions,
  ): Promise<{ hasOffendingExecutableFiles: boolean } | { error: string }>;
  // iOS only
  checkPrivateWrite(): Promise<
    { canWritePrivate: boolean } | { error: string }
  >;
  // iOS only
  hasThirdPartyAppStore(): Promise<
    { hasThirdPartyAppStore: boolean } | { error: string }
  >;
  isValid(value: unknown): value is boolean | string;
  isError(value: unknown): value is DeviceAuthenticityError;
}
