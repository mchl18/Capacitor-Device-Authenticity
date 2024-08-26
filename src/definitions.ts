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
  isJailbroken(): Promise<{ isJailbroken: boolean } | { error: string }>;
  // Android only
  isRooted(): Promise<{ isRooted: boolean } | { error: string }>;
  // Android only
  isInstalledFromAllowedStore(): Promise<
    { isInstalledFromAllowedStore: boolean } | { error: string }
  >;
  // Android only
  getApkCertSignature(): Promise<
    { apkCertSignature: string } | { error: string }
  >;
  // Android only
  checkApkCertSignature(
    expectedApkSignature: string,
  ): Promise<{ apkCertSignatureMatches: boolean } | { error: string }>;
  // Android only
  checkTags(): Promise<{ hasTags: boolean } | { error: string }>;
  checkPaths(): Promise<{ hasPaths: boolean } | { error: string }>;
  // Android only
  checkExecutableFiles(): Promise<
    { hasExecutableFiles: boolean } | { error: string }
  >;
  // iOS only
  checkPrivateWrite(): Promise<{ canWritePrivate: boolean } | { error: string }>;
  // iOS only
  hasCydia(): Promise<{ hasCydia: boolean } | { error: string }>;
  isValid(value: unknown): value is boolean | string;
  isError(value: unknown): value is DeviceAuthenticityError;
}
