import type { DeviceAuthenticityError } from './types';

export function isValid(value: unknown): value is boolean | string {
  return (
    typeof value === 'boolean' ||
    (typeof value === 'string' && !('error' in (value as never as object)))
  );
}

export function isError(value: unknown): value is DeviceAuthenticityError {
  return typeof value === 'object' && value !== null && 'error' in value;
}
