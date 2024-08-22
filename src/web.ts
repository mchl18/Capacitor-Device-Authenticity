import { WebPlugin } from '@capacitor/core';

import type { DeviceAuthenticityPlugin } from './definitions';
import type { DeviceAuthenticityResult } from './types';

export class DeviceAuthenticityWeb
  extends WebPlugin
  implements DeviceAuthenticityPlugin
{
  async checkAuthenticity(): Promise<DeviceAuthenticityResult> {
    return { error: 'Not available on web' };
  }
}
