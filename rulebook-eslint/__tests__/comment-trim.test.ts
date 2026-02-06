import { describe, it } from 'vitest';
import commentTrim from '../src/rules/comment-trim';
import { assertProperties, AssertThat, assertThatRule } from './tests';

describe('CommentTrimTest', () => {
    const assertThat: AssertThat = assertThatRule(commentTrim, 'comment-trim');

    it('Rule properties', () => assertProperties(commentTrim));

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
