# capacitor-device-authenticity

Check the authenticity of an Ionic Capacitor app

This plugin provides methods to check whether a device is jailbroken/rooted, inside an emulator, or not installed from the app store.

## Install

```bash
npm install capacitor-device-authenticity
npx cap sync
```

## API

<docgen-index>

* [`checkAuthenticity(...)`](#checkauthenticity)
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


### Type Aliases


#### DeviceAuthenticityResult

<code>{ // Android only isRooted?: boolean; // Both Android and iOS isEmulator?: boolean; // Android only isInstalledFromPlayStore?: boolean; // iOS only isJailbroken?: boolean; error?: string; }</code>


#### DeviceAuthenticityOptions

<code>{ allowedStores?: string[]; }</code>

</docgen-api>
