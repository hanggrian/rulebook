import { type AssertThat, assertThatRule } from 'testing/dist/asserters';
import { describe, it } from 'vitest';
import commentTrimRule from '../../rules/comment-trim';
import assertProperties from '../asserts';

describe('CommentTrimRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(commentTrimRule, 'comment-trim');

    it('Rule properties', () => assertProperties(commentTrimRule));

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
                "Remove blank line after '//'.",
                "Remove blank line after '//'.",
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
