import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import parenthesesTrimRule from '../../rules/parentheses-trim';
import assertProperties from '../asserts';

describe('ParenthesesTrimRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(parenthesesTrimRule);

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
                "2:29 Remove blank line after '('.",
                "6:17 Remove blank line before ')'.",
                "7:33 Remove blank line after '['.",
                "11:21 Remove blank line before ']'.",
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
