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
  isJailbroken(): Promise<{ isJailbroken: boolean } | { error: string }>;
  isRooted(): Promise<{ isRooted: boolean } | { error: string }>;
  isInstalledFromAllowedStore(): Promise<
    { isInstalledFromAllowedStore: boolean } | { error: string }
  >;
  getApkCertSignature(): Promise<
    { apkCertSignature: string } | { error: string }
  >;
  checkApkCertSignature(
    expectedApkSignature: string,
  ): Promise<{ apkCertSignatureMatches: boolean } | { error: string }>;
  checkTags(): Promise<{ hasTags: boolean } | { error: string }>;
  checkPaths(): Promise<{ hasPaths: boolean } | { error: string }>;
  checkExecutableFiles(): Promise<
    { hasExecutableFiles: boolean } | { error: string }
  >;
  isValid(value: unknown): value is boolean | string;
  isError(value: unknown): value is DeviceAuthenticityError;
}
