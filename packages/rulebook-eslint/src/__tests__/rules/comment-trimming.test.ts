import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import commentTrimmingRule from '../../rules/comment-trimming';
import assertProperties from '../asserts';

describe('CommentTrimmingRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(commentTrimmingRule);

    it('Rule properties', () => assertProperties(commentTrimmingRule));

    it(
        'Comment without initial and final newline',
        () =>
            assertThat(
                `
                function foo() {
                    // Lorem ipsum.
                }
                `,
            ).hasNoError(),
    );

    it(
        'Comment with initial and final newline',
        () =>
            assertThat(
                `
                function foo() {
                    //
                    // Lorem ipsum.
                    //
                }
                `,
            ).hasErrorMessages(
                "3:21 Remove blank line after '//'.",
                "5:21 Remove blank line after '//'.",
            ),
    );

    it(
        'Skip blank comment',
        () =>
            assertThat(
                `
                function foo() {

                    //

                }
                `,
            ).hasNoError(),
    );

    it(
        'Skip comment with code',
        () =>
            assertThat(
                `
                function foo() {
                    console.log(); //
                    console.log(); // Lorem ipsum.
                    console.log(); //
                }
                `,
            ).hasNoError(),
    );
});
