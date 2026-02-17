import { describe, it } from 'vitest';
import todoCommentRule from '../../rules/todo-comment';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('TodoCommentRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(todoCommentRule, 'todo-comment');

    it('Rule properties', () => assertProperties(todoCommentRule));

    it(
        'Uppercase TODO comments',
        () =>
            assertThat(
                `
                // TODO add tests
                // FIXME fix bug
                `,
            ).hasNoError(),
    );

    it(
        'Lowercase TODO comments',
        () =>
            assertThat(
                `
                // todo add tests
                // fixme fix bug
                `,
            ).hasErrorMessages(
                "Capitalize keyword 'todo'.",
                "Capitalize keyword 'fixme'.",
            ),
    );

    it(
        'Unknown TODO comments',
        () =>
            assertThat(
                `
                // TODO: add tests
                // FIXME1 fix bug
                `,
            ).hasErrorMessages(
                "Omit separator ':'.",
                "Omit separator '1'.",
            ),
    );

    it(
        'TODOs in block comments',
        () =>
            assertThat(
                `
                /** todo add tests */

                /**
                 * FIXME: memory leak
                 */
                `,
            ).hasErrorMessages(
                "Capitalize keyword 'todo'.",
                "Omit separator ':'.",
            ),
    );

    it(
        'TODO keyword mid-sentence',
        () =>
            assertThat(
                `
                // Untested. Todo: add tests.
                `,
            ).hasErrorMessages(
                "Capitalize keyword 'Todo'.",
                "Omit separator ':'.",
            ),
    );
});
