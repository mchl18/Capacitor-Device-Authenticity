import type { DeviceAuthenticityError } from './types';

export function isError(value: unknown): value is DeviceAuthenticityError {
  return typeof value === 'object' && value !== null && 'error' in value;
}

export function isValid(value: unknown): value is boolean | string {
  return !isError(value);
}
