import { registerPlugin } from '@capacitor/core';

import type { DeviceAuthenticityPlugin } from './definitions';
import { isValid, isError } from './utils';

const DeviceAuthenticity = registerPlugin<DeviceAuthenticityPlugin>(
  'DeviceAuthenticity',
  {
    web: () => import('./web').then(m => new m.DeviceAuthenticityWeb()),
  },
);

export * from './definitions';
export { DeviceAuthenticity, isError, isValid };
