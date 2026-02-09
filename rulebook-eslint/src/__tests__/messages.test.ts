import { describe, expect, it } from 'vitest';
import Messages from '../messages';

describe('MessagesTest', () => {
    it(
        'get',
        () => expect(Messages.get('wildcard.import')).toBe('Use single-type import.'),
    );
});
