import { describe, expect, it } from 'vitest';
import messages from '../messages';

describe('MessagesTest', () => {
    it(
        'get',
        () => expect(messages.get('no.empty.file')).toBe('Delete the empty file.'),
    );
});
