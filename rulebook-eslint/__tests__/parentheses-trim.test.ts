import { describe, it } from 'vitest';
import parenthesesTrim from '../src/rules/parentheses-trim';
import { assertProperties, AssertThat, assertThatRule } from './tests';

describe('ParenthesesTrimTest', () => {
    const assertThat: AssertThat = assertThatRule(parenthesesTrim, 'parentheses-trim');

    it('Rule properties', () => assertProperties(parenthesesTrim));

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
