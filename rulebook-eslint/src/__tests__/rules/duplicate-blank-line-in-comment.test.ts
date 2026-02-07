import { describe, it } from 'vitest';
import duplicateBlankLineInComment from '../../rules/duplicate-blank-line-in-comment';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('DuplicateBlankLineInCommentTest', () => {
    const assertThat: AssertThat =
        assertThatRule(duplicateBlankLineInComment, 'duplicate-blank-line-in-comment');

    it('Rule properties', () => assertProperties(duplicateBlankLineInComment));

    it(
        'Single empty line in EOL comment',
        () =>
            assertThat(
                `
                function foo() {
                    // Lorem ipsum
                    //
                    // dolor sit amet.
                }
                `,
            ).hasNoError(),
    );

    it(
        'Multiple empty lines in EOL comment',
        () =>
            assertThat(
                `
                function foo() {
                    // Lorem ipsum
                    //
                    //
                    // dolor sit amet.
                }
                `,
            ).hasErrorMessages("Remove consecutive blank line after '//'"),
    );
});
