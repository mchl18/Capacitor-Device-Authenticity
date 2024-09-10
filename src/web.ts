import { WebPlugin } from '@capacitor/core';

import type { DeviceAuthenticityPlugin } from './definitions';
import type {
  DeviceAuthenticityError,
  DeviceAuthenticityResult,
} from './types';

export class DeviceAuthenticityWeb
  extends WebPlugin
  implements DeviceAuthenticityPlugin
{
  static DEFAULT_ANDROID_ROOT_INDICATOR_TAGS = [
    'test-keys',
    'dev-keys',
    'userdebug',
    'engineering',
    'release-keys-debug',
    'custom',
    'rooted',
    'supersu',
    'magisk',
    'lineage',
    'unofficial',
  ];

  static DEFAULT_IOS_JAILBREAK_PATHS = [
    '/Applications/Cydia.app',
    '/Applications/Sileo.app',
    '/Applications/Zebra.app',
    '/Applications/Installer.app',
    '/Applications/Unc0ver.app',
    '/Applications/Checkra1n.app',
    '/Library/MobileSubstrate/MobileSubstrate.dylib',
    '/usr/sbin/sshd',
    '/usr/bin/sshd',
    '/usr/libexec/sftp-server',
    '/etc/apt',
    '/private/var/lib/apt/',
    '/private/var/mobile/Library/Cydia/',
    '/private/var/stash',
    '/private/var/db/stash',
    '/private/var/jailbreak',
    '/var/mobile/Library/SBSettings/Themes',
  ];

  static DEFAULT_ANDROID_ROOTED_PATHS = [
    '/system/app/Superuser.apk',
    '/sbin/su',
    '/system/bin/su',
    '/system/xbin/su',
    '/data/local/xbin/su',
    '/data/local/bin/su',
    '/system/sd/xbin/su',
    '/system/bin/failsafe/su',
    '/data/local/su',
    '/su/bin/su',
  ];

  async checkAuthenticity(): Promise<DeviceAuthenticityResult> {
    return { error: 'Not available on web' };
  }

  async isEmulator(): Promise<{ error: string } | { isEmulator: boolean }> {
    return { error: 'Not available on web' };
  }

  async isJailbroken(): Promise<{ error: string } | { isJailbroken: boolean }> {
    return { error: 'Not available on web' };
  }

  async isRooted(): Promise<{ error: string } | { isRooted: boolean }> {
    return { error: 'Not available on web' };
  }

  async isNotInstalledFromAllowedStore(): Promise<
    { error: string } | { isNotInstalledFromAllowedStore: boolean }
  > {
    return { error: 'Not available on web' };
  }

  async getApkCertSignature(): Promise<
    { error: string } | { apkCertSignature: string }
  > {
    return { error: 'Not available on web' };
  }

  async checkApkCertSignature(): Promise<
    { error: string } | { apkCertSignatureMatches: boolean }
  > {
    return { error: 'Not available on web' };
  }

  async checkTags(): Promise<{ error: string } | { hasOffendingTags: boolean }> {
    return { error: 'Not available on web' };
  }

  async checkPaths(): Promise<{ error: string } | { hasOffendingPaths: boolean }> {
    return { error: 'Not available on web' };
  }

  async checkExecutableFiles(): Promise<
    { error: string } | { hasOffendingExecutableFiles: boolean }
  > {
    return { error: 'Not available on web' };
  }
  async hasThirdPartyAppStore(): Promise<
    { error: string } | { hasThirdPartyAppStore: boolean }
  > {
    return { error: 'Not available on web' };
  }

  async checkPrivateWrite(): Promise<
    { error: string } | { canWritePrivate: boolean }
  > {
    return { error: 'Not available on web' };
  }

  // In order to check a value we need to use the type guards `isValid` and `isError` along with a cast to boolean if it is not an error.
  isValid(value: unknown): value is boolean | string {
    return (
      typeof value === 'boolean' ||
      (typeof value === 'string' && !('error' in (value as never as object)))
    );
  }
  // In order to check a value we need to use the type guards `isValid` and `isError` along with a cast to boolean if it is not an error.
  isError(value: unknown): value is DeviceAuthenticityError {
    return typeof value === 'object' && value !== null && 'error' in value;
  }
}
