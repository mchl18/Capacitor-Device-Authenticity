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

- [`checkAuthenticity(...)`](#checkauthenticity)
- [Type Aliases](#type-aliases)

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

---

### Type Aliases

#### DeviceAuthenticityResult

<code>{ // Android only isRooted?: boolean; // Both Android and iOS isEmulator?: boolean; // Android only isInstalledFromAllowedStore?: boolean; // iOS only isJailbroken?: boolean; // Android only // If it is a string, it is the expected to be an error message. apkSignatureMatch?: boolean | string; error?: string; }</code>

#### DeviceAuthenticityOptions

<code>{ allowedStores?: string[]; apkSignature?: string; }</code>

</docgen-api>

## Type checking:

In order to check a value we need to use the type guards `isValid` and `isError` along with a cast to boolean if it is not an error.

```typescript
const result = await DeviceAuthenticityWeb.checkTags();
if (DeviceAuthenticityWeb.isValid(result)) {
  const hasSuspiciousTags: boolean = result;
} else {
  const error: DeviceAuthenticityError = result;
}
```

### TODO

- [ ] pass overrides in both plugins
