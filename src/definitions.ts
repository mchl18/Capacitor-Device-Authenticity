import { DeviceAuthenticityOptions, DeviceAuthenticityResult } from './types';

export interface DeviceAuthenticityPlugin {
  checkAuthenticity(
    options?: DeviceAuthenticityOptions,
  ): Promise<DeviceAuthenticityResult>;
}
