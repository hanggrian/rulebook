import { describe, expect, it } from 'vitest';
import Messages from '../messages';

describe('MessagesTest', () => {
    it(
        'get',
        () => expect(Messages.get('generic.name')).toBe('Use single uppercase letter.'),
    );
});
