import { describe, it } from 'vitest';
import parenthesesTrimRule from '../../rules/parentheses-trim';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('ParenthesesTrimRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(parenthesesTrimRule, 'parentheses-trim');

    it('Rule properties', () => assertProperties(parenthesesTrimRule));

    it(
        'Parentheses without newline padding',
        () =>
            assertThat(
                `
                function foo(
                    bar,
                ) {
                    const qux = [
                        3,
                    ];
                }
                `,
            ).hasNoError(),
    );

    it(
        'Parentheses with newline padding',
        () =>
            assertThat(
                `
                function foo(

                    bar,

                ) {
                    const qux = [

                        3,

                    ];
                }
                `,
            ).hasErrorMessages(
                "Remove blank line after '('.",
                "Remove blank line before ')'.",
                "Remove blank line after '['.",
                "Remove blank line before ']'.",
            ),
    );

    it(
        'Comments are considered content',
        () =>
            assertThat(
                `
                function foo(
                    // Lorem ipsum.
                    bar,
                    // Lorem ipsum.
                ) {
                    const qux = [
                        /* Lorem ipsum. */
                        3,
                        /* Lorem ipsum. */
                    ];
                }
                `,
            ).hasNoError(),
    );
});
