import { describe, expect, it } from 'vitest';
import messages from '../messages';

describe('MessagesTest', () => {
    it(
        'get',
        () => expect(messages.get('wildcard.import')).toBe('Use single-type import.'),
    );
});
