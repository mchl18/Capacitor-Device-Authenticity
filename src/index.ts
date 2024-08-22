import { registerPlugin } from '@capacitor/core';

import type { DeviceAuthenticityPlugin } from './definitions';

const DeviceAuthenticity = registerPlugin<DeviceAuthenticityPlugin>(
  'DeviceAuthenticity',
  {
    web: () => import('./web').then(m => new m.DeviceAuthenticityWeb()),
  },
);

export * from './definitions';
export { DeviceAuthenticity };
