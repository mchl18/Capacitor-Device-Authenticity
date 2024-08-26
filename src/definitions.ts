import { DeviceAuthenticityError, DeviceAuthenticityOptions, DeviceAuthenticityResult } from './types';

export interface DeviceAuthenticityPlugin {
  checkAuthenticity(
    options?: DeviceAuthenticityOptions,
  ): Promise<DeviceAuthenticityResult>;

  isEmulator(): Promise<boolean | { error: string }>;
  isJailbroken(): Promise<boolean | { error: string }>;
  isRooted(): Promise<boolean | { error: string }>;
  isInstalledFromAllowedStore(): Promise<boolean | { error: string }>;
  getApkCertSignature(): Promise<boolean | { error: string }>;
  checkApkCertSignature(
    expectedApkSignature: string,
  ): Promise<boolean | { error: string }>;
  checkTags(): Promise<boolean | { error: string }>;
  checkPaths(): Promise<boolean | { error: string }>;
  checkExecutableFiles(): Promise<boolean | { error: string }>;
  isValid(value: unknown): value is boolean | string;
  isError(value: unknown): value is DeviceAuthenticityError;
}
