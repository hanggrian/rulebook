import { type AssertThat, assertThatRule } from 'testing/src/asserters';
import { describe, it } from 'vitest';
import todoCommentRule from '../../rules/todo-comment';
import assertProperties from '../asserts';

describe('TodoCommentRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(todoCommentRule);

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
                "2:17 Capitalize keyword 'todo'.",
                "3:17 Capitalize keyword 'fixme'.",
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
                "2:17 Omit separator ':'.",
                "3:17 Omit separator '1'.",
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
                "2:17 Capitalize keyword 'todo'.",
                "4:17 Omit separator ':'.",
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
                "2:17 Capitalize keyword 'Todo'.",
                "2:17 Omit separator ':'.",
            ),
    );
});
