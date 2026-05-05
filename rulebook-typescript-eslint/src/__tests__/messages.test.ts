import { describe, expect, it } from 'vitest';
import messages from '../messages';

describe('MessagesTest', () => {
    it(
        'get',
        () => expect(messages.get('generic.name')).toBe('Use pascal-case name.'),
    );
});
