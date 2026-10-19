import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import parenthesesTrimmingRule from '../../rules/parentheses-trimming';
import assertProperties from '../asserts';

describe('ParenthesesTrimmingRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(parenthesesTrimmingRule);

    it('Rule properties', () => assertProperties(parenthesesTrimmingRule));

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
