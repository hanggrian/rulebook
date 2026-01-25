import { describe, it } from 'vitest';
import todoComment, { MSG_KEYWORD, MSG_SEPARATOR } from '../rules/todo-comment';
import messages from '../messages';
import { AssertThat, assertThatRule } from './tests';

describe('TodoCommentTest', () => {
    const assertThat: AssertThat = assertThatRule(todoComment, 'todo-comment');

    it('Uppercase TODO comments', () =>
        assertThat(
            `
            // TODO add tests
            // FIXME fix bug
            `,
        ).hasNoError(),
    );

    it('Lowercase TODO comments', () =>
        assertThat(
            `
            // todo add tests
            // fixme fix bug
            `,
        ).hasErrorMessages(
            messages.of(MSG_KEYWORD, 'todo'),
            messages.of(MSG_KEYWORD, 'fixme'),
        ),
    );

    it('Unknown TODO comments', () =>
        assertThat(
            `
            // TODO: add tests
            // FIXME1 fix bug
            `,
        ).hasErrorMessages(
            messages.of(MSG_SEPARATOR, ':'),
            messages.of(MSG_SEPARATOR, '1'),
        ),
    );

    it('TODOs in block comments', () =>
        assertThat(
            `
            /** todo add tests */

            /**
             * FIXME: memory leak
             */
            `,
        ).hasErrorMessages(
            messages.of(MSG_KEYWORD, 'todo'),
            messages.of(MSG_SEPARATOR, ':'),
        ),
    );

    it('TODO keyword mid-sentence', () =>
        assertThat(
            `
            // Untested. Todo: add tests.
            `,
        ).hasErrorMessages(
            messages.of(MSG_KEYWORD, 'Todo'),
            messages.of(MSG_SEPARATOR, ':'),
        ),
    );
});
