import { WebPlugin } from '@capacitor/core';

import type { DeviceAuthenticityPlugin } from './definitions';
import type {
  DeviceAuthenticityError,
  DeviceAuthenticityResult,
} from './types';
import { isError } from './utils';

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

  async checkAuthenticity(): Promise<
    DeviceAuthenticityResult | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  async isEmulator(): Promise<
    { isEmulator: boolean } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  async isJailbroken(): Promise<
    { isJailbroken: boolean } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  async isRooted(): Promise<{ isRooted: boolean } | DeviceAuthenticityError> {
    return { error: 'Not available on web' };
  }

  async isNotInstalledFromAllowedStore(): Promise<
    { isNotInstalledFromAllowedStore: boolean } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  async getApkCertSignature(): Promise<
    { apkCertSignature: string } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  async checkApkCertSignature(): Promise<
    { apkCertSignatureMatches: boolean } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  async checkTags(): Promise<
    { hasOffendingTags: boolean } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  async checkPaths(): Promise<
    { hasOffendingPaths: boolean } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  async checkExecutableFiles(): Promise<
    { hasOffendingExecutableFiles: boolean } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }
  async hasThirdPartyAppStore(): Promise<
    { hasThirdPartyAppStore: boolean } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  async checkPrivateWrite(): Promise<
    { canWritePrivate: boolean } | DeviceAuthenticityError
  > {
    return { error: 'Not available on web' };
  }

  isValid = (value: unknown): value is boolean | string => !isError(value);
  isError = isError;
}
