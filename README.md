# capacitor-device-authenticity

Check the authenticity of an Ionic Capacitor app

This plugin provides methods to check whether a device is jailbroken/rooted, inside an emulator, or not installed from the app store.

This software is provided under the MIT License. The code included in this project, particularly the jailbreak detection method, is provided "as is" without warranty of any kind, express or implied.
The authors and copyright holders of this software shall not be liable for any claim, damages, or other liability, whether in an action of contract, tort, or otherwise, arising from, out of, or in connection with the software or the use or other dealings in the software.
Important Notes:

The jailbreak detection method provided is not guaranteed to be foolproof or comprehensive. It may not detect all instances of jailbroken devices or third-party app stores.
This code is intended for informational and educational purposes only. It should not be relied upon as a sole means of security or integrity checking in production environments.
The use of this code may have implications related to user privacy and device autonomy. Ensure that your use of this code complies with all applicable laws, regulations, and platform policies.
The developers of this software do not encourage or endorse the unauthorized modification of devices or violation of terms of service of any platform.
Users and implementers of this code are responsible for ensuring their own compliance with all relevant policies, laws, and regulations.

By using this software, you acknowledge that you have read this disclaimer and agree to its terms.

IMPORTANT: This plugin is still under development and is not yet ready for production use. The author has yet to verify if these calls trigger security warnings or cause app store rejections.


[![HitCount](https://hits.dwyl.com/mchl18/capacitor-device-authenticity.svg)](https://hits.dwyl.com/mchl18/capacitor-device-authenticity)


[![https://nodei.co/npm/capacitor-device-authenticity.png?downloads=true&downloadRank=true&stars=true](https://nodei.co/npm/capacitor-device-authenticity.png?downloads=true&downloadRank=true&stars=true)](https://www.npmjs.com/package/capacitor-device-authenticity)

## Install

```bash
npm install capacitor-device-authenticity
npx cap sync
```

## API

<docgen-index>

* [`checkAuthenticity(...)`](#checkauthenticity)
* [`isEmulator()`](#isemulator)
* [`isJailbroken(...)`](#isjailbroken)
* [`isRooted(...)`](#isrooted)
* [`isInstalledFromAllowedStore(...)`](#isinstalledfromallowedstore)
* [`getApkCertSignature()`](#getapkcertsignature)
* [`checkApkCertSignature(...)`](#checkapkcertsignature)
* [`checkTags(...)`](#checktags)
* [`checkPaths(...)`](#checkpaths)
* [`checkExecutableFiles(...)`](#checkexecutablefiles)
* [`checkPrivateWrite()`](#checkprivatewrite)
* [`hasThirdPartyAppStore()`](#hasthirdpartyappstore)
* [`isValid(...)`](#isvalid)
* [`isError(...)`](#iserror)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### checkAuthenticity(...)

```typescript
checkAuthenticity(options?: DeviceAuthenticityOptions | undefined) => Promise<DeviceAuthenticityResult>
```

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deviceauthenticityoptions">DeviceAuthenticityOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#deviceauthenticityresult">DeviceAuthenticityResult</a>&gt;</code>

--------------------


### isEmulator()

```typescript
isEmulator() => Promise<{ isEmulator: boolean; } | { error: string; }>
```

**Returns:** <code>Promise&lt;{ isEmulator: boolean; } | { error: string; }&gt;</code>

--------------------


### isJailbroken(...)

```typescript
isJailbroken(options?: DeviceAuthenticityJailbreakOptions | undefined) => Promise<{ isJailbroken: boolean; } | { error: string; }>
```

| Param         | Type                                                                                              |
| ------------- | ------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deviceauthenticityjailbreakoptions">DeviceAuthenticityJailbreakOptions</a></code> |

**Returns:** <code>Promise&lt;{ isJailbroken: boolean; } | { error: string; }&gt;</code>

--------------------


### isRooted(...)

```typescript
isRooted(options?: DeviceAuthenticityRootedOptions | undefined) => Promise<{ isRooted: boolean; } | { error: string; }>
```

| Param         | Type                                                                                        |
| ------------- | ------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deviceauthenticityrootedoptions">DeviceAuthenticityRootedOptions</a></code> |

**Returns:** <code>Promise&lt;{ isRooted: boolean; } | { error: string; }&gt;</code>

--------------------


### isInstalledFromAllowedStore(...)

```typescript
isInstalledFromAllowedStore(options?: DeviceAuthenticityInstalledFromAllowedStoreOptions | undefined) => Promise<{ isInstalledFromAllowedStore: boolean; } | { error: string; }>
```

| Param         | Type                                                                                                                              |
| ------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deviceauthenticityinstalledfromallowedstoreoptions">DeviceAuthenticityInstalledFromAllowedStoreOptions</a></code> |

**Returns:** <code>Promise&lt;{ isInstalledFromAllowedStore: boolean; } | { error: string; }&gt;</code>

--------------------


### getApkCertSignature()

```typescript
getApkCertSignature() => Promise<{ apkCertSignature: string; } | { error: string; }>
```

**Returns:** <code>Promise&lt;{ apkCertSignature: string; } | { error: string; }&gt;</code>

--------------------


### checkApkCertSignature(...)

```typescript
checkApkCertSignature(options?: DeviceAuthenticityCheckApkCertSignatureOptions | undefined) => Promise<{ apkCertSignatureMatches: boolean; } | { error: string; }>
```

| Param         | Type                                                                                                                      |
| ------------- | ------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deviceauthenticitycheckapkcertsignatureoptions">DeviceAuthenticityCheckApkCertSignatureOptions</a></code> |

**Returns:** <code>Promise&lt;{ apkCertSignatureMatches: boolean; } | { error: string; }&gt;</code>

--------------------


### checkTags(...)

```typescript
checkTags(options?: DeviceAuthenticityCheckTagsOptions | undefined) => Promise<{ hasTags: boolean; } | { error: string; }>
```

| Param         | Type                                                                                              |
| ------------- | ------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deviceauthenticitychecktagsoptions">DeviceAuthenticityCheckTagsOptions</a></code> |

**Returns:** <code>Promise&lt;{ hasTags: boolean; } | { error: string; }&gt;</code>

--------------------


### checkPaths(...)

```typescript
checkPaths(options?: DeviceAuthenticityCheckPathsOptions | undefined) => Promise<{ hasPaths: boolean; } | { error: string; }>
```

| Param         | Type                                                                                                |
| ------------- | --------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deviceauthenticitycheckpathsoptions">DeviceAuthenticityCheckPathsOptions</a></code> |

**Returns:** <code>Promise&lt;{ hasPaths: boolean; } | { error: string; }&gt;</code>

--------------------


### checkExecutableFiles(...)

```typescript
checkExecutableFiles(options?: DeviceAuthenticityCheckExecutableFilesOptions | undefined) => Promise<{ hasExecutableFiles: boolean; } | { error: string; }>
```

| Param         | Type                                                                                                                    |
| ------------- | ----------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deviceauthenticitycheckexecutablefilesoptions">DeviceAuthenticityCheckExecutableFilesOptions</a></code> |

**Returns:** <code>Promise&lt;{ hasExecutableFiles: boolean; } | { error: string; }&gt;</code>

--------------------


### checkPrivateWrite()

```typescript
checkPrivateWrite() => Promise<{ canWritePrivate: boolean; } | { error: string; }>
```

**Returns:** <code>Promise&lt;{ canWritePrivate: boolean; } | { error: string; }&gt;</code>

--------------------


### hasThirdPartyAppStore()

```typescript
hasThirdPartyAppStore() => Promise<{ hasThirdPartyAppStore: boolean; } | { error: string; }>
```

**Returns:** <code>Promise&lt;{ hasThirdPartyAppStore: boolean; } | { error: string; }&gt;</code>

--------------------


### isValid(...)

```typescript
isValid(value: unknown) => value is string | boolean
```

| Param       | Type                 |
| ----------- | -------------------- |
| **`value`** | <code>unknown</code> |

**Returns:** <code>boolean</code>

--------------------


### isError(...)

```typescript
isError(value: unknown) => value is DeviceAuthenticityError
```

| Param       | Type                 |
| ----------- | -------------------- |
| **`value`** | <code>unknown</code> |

**Returns:** <code>boolean</code>

--------------------


### Type Aliases


#### DeviceAuthenticityResult

<code>{ // Android only isRooted?: boolean; // Both Android and iOS isEmulator?: boolean; // Android only isInstalledFromAllowedStore?: boolean; // iOS only isJailbroken?: boolean; // Android only // If it is a string, it is the expected to be an error message. apkSignatureMatch?: boolean; // iOS only hasPaths?: boolean; // iOS only canWritePrivate?: boolean; // iOS only hasThirdPartyAppStore?: boolean; error?: string; }</code>


#### DeviceAuthenticityOptions

<code>{ // Android only allowedStores?: string[]; // Android only apkSignature?: string; // Android only // Override for the default root indicator paths which are: // "/system/app/Superuser.apk", // "/sbin/su", // "/system/bin/su", // "/system/xbin/su", // "/data/local/xbin/su", // "/data/local/bin/su", // "/system/sd/xbin/su", // "/system/bin/failsafe/su", // "/data/local/su", // "/su/bin/su" rootIndicatorPaths?: string[]; // Android only // Override for the default root indicator tags which are: // "test-keys", // Common for many rooted devices // "dev-keys", // Development keys, often seen in custom ROMs // "userdebug", // User-debuggable build, common in rooted devices // "engineering", // Engineering build, may indicate a modified system // "release-keys-debug", // Debug version of release keys // "custom", // Explicitly marked as custom // "rooted", // Explicitly marked as rooted (rare, but possible) // "supersu", // Indicates SuperSU rooting tool // "magisk", // Indicates Magisk rooting framework // "lineage", // LineageOS custom ROM // "unofficial" // Unofficial build, common in custom ROMs // If you are planning to extend the list, please do as follow: // const completeList = [...DeviceAuthenticityWeb.DEFAULT_ANDROID_ROOT_INDICATOR_TAGS, ...yourList]; // Then use completeList in the plugin. Otherwise, the default list will be used. rootIndicatorTags?: string[]; // iOS only // Override for the default jailbreak paths which are: // "/Applications/Cydia.app", // "/Library/MobileSubstrate/MobileSubstrate.dylib", // "/bin/bash", // "/usr/sbin/sshd", // "/etc/apt", // "/private/var/lib/apt/" // If you are planning to extend the list, please do as follow: // const completeList = [...DeviceAuthenticityWeb.DEFAULT_IOS_JAILBREAK_PATHS, ...yourList]; // Then use completeList in the plugin. Otherwise, the default list will be used. jailbreakIndicatorPaths?: string[]; // iOS only // Override for the default forbidden schemas which are: // "cydia://", // "sileo://", // "zbra://", // "filza://", // "undecimus://", // "activator://" forbiddenSchemas?: string[]; // Android only // Override for the default rooted paths which are: // "/system/app/Superuser.apk", // "/sbin/su", // "/system/bin/su", // "/system/xbin/su", // "/data/local/xbin/su", // "/data/local/bin/su", // "/system/sd/xbin/su", // "/system/bin/failsafe/su", // "/data/local/su", // "/su/bin/su" // If you are planning to extend the list, please do as follow: // const completeList = [...DeviceAuthenticityWeb.DEFAULT_ANDROID_ROOTED_PATHS, ...yourList]; // Then use completeList in the plugin. Otherwise, the default list will be used. androidRootedPaths?: string[]; }</code>


#### DeviceAuthenticityJailbreakOptions

<code>{ jailbreakIndicatorPaths?: string[]; forbiddenSchemas?: string[]; }</code>


#### DeviceAuthenticityRootedOptions

<code>{ rootIndicatorPaths?: string[]; rootIndicatorTags?: string[]; rootIndicatorFiles?: string[]; }</code>


#### DeviceAuthenticityInstalledFromAllowedStoreOptions

<code>{ allowedStores?: string[]; }</code>


#### DeviceAuthenticityCheckApkCertSignatureOptions

<code>{ expectedApkSignature: string; }</code>


#### DeviceAuthenticityCheckTagsOptions

<code>{ rootIndicatorTags: string[]; }</code>


#### DeviceAuthenticityCheckPathsOptions

<code>{ jailbreakIndicatorPaths: string[]; }</code>


#### DeviceAuthenticityCheckExecutableFilesOptions

<code>{ rootIndicatorFiles: string[]; }</code>


#### DeviceAuthenticityError

<code>{ error: string; }</code>

</docgen-api>

### Type checking:

In order to check a value we need to use the type guards `isValid` and `isError` along with a cast to boolean if it is not an error.

```typescript
const result = await DeviceAuthenticityWeb.checkTags();
if (DeviceAuthenticityWeb.isValid(result)) {
  const hasSuspiciousTags: boolean = result;
} else {
  const error: DeviceAuthenticityError = result;
}
```

### Example usage:

(will try and simplify this)

```typescript
  // only available on ios and android
  if (Capacitor.getPlatform() !== 'web') {
    // TODO: add options to checkAuthenticity
    const authenticityResult = await DeviceAuthenticity.checkAuthenticity();
    const failedChecks: string[] = [];
    // TODO: put into plugin if possible
    if (platform === 'ios') {
      if (authenticityResult.isEmulator) failedChecks.push('isEmulator');
      if (authenticityResult.isJailbroken) failedChecks.push('isJailbroken');
      if (authenticityResult.hasPaths) failedChecks.push('hasPaths');
      if (authenticityResult.hasThirdPartyAppStore)
        failedChecks.push('hasThirdPartyAppStore');
    } else if (platform === 'android') {
      if (authenticityResult.isRooted) failedChecks.push('isRooted');
      if (authenticityResult.isEmulator) failedChecks.push('isEmulator');
      if (!authenticityResult.apkSignatureMatch)
        failedChecks.push('apkSignatureMatch');
      if (!authenticityResult.isInstalledFromAllowedStore)
        failedChecks.push('isInstalledFromAllowedStore');
    }

    if (authenticityResult.error) {
      failedChecks.push('error: ' + authenticityResult.error);
    }

    if (failedChecks.length > 0) {
      alert(
        'Could not verify your device. Failed checks: ' +
          failedChecks.join(', ')
      );
      App.exitApp();
    }
  }
```

### TODO

- [ ] pass overrides in both plugins
