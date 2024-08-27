import {
  DeviceAuthenticityError,
  DeviceAuthenticityOptions,
  DeviceAuthenticityResult,
} from './types';

export interface DeviceAuthenticityPlugin {
  checkAuthenticity(
    options?: DeviceAuthenticityOptions,
  ): Promise<DeviceAuthenticityResult>;

  isEmulator(): Promise<{ isEmulator: boolean } | { error: string }>;
  // iOS only
  isJailbroken({
    jailbreakIndicatorPaths,
    forbiddenSchemes,
  }: {
    jailbreakIndicatorPaths?: string[];
    forbiddenSchemes?: string[];
  }): Promise<{ isJailbroken: boolean } | { error: string }>;
  // Android only
  isRooted({
    rootIndicatorPaths,
    rootIndicatorTags,
  }: {
    rootIndicatorPaths?: string[];
    rootIndicatorTags?: string[];
    rootIndicatorFiles?: string[];
  }): Promise<{ isRooted: boolean } | { error: string }>;
  // Android only
  isInstalledFromAllowedStore({
    allowedStores,
  }: {
    allowedStores: string[];
  }): Promise<{ isInstalledFromAllowedStore: boolean } | { error: string }>;
  // Android only
  getApkCertSignature(): Promise<
    { apkCertSignature: string } | { error: string }
  >;
  // Android only
  checkApkCertSignature({
    expectedApkSignature,
  }: {
    expectedApkSignature: string;
  }): Promise<{ apkCertSignatureMatches: boolean } | { error: string }>;
  // Android only
  checkTags({
    rootIndicatorTags,
  }: {
    rootIndicatorTags: string[];
  }): Promise<{ hasTags: boolean } | { error: string }>;
  checkPaths({
    jailbreakIndicatorPaths,
  }: {
    jailbreakIndicatorPaths: string[];
  }): Promise<{ hasPaths: boolean } | { error: string }>;
  // Android only
  checkExecutableFiles(): Promise<
    { hasExecutableFiles: boolean } | { error: string }
  >;
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
