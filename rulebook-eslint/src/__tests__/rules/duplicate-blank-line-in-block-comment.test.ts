import { describe, it } from 'vitest';
import duplicateBlankLineInBlockComment from '../../rules/duplicate-blank-line-in-block-comment';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('DuplicateBlankLineInBlockCommentTest', () => {
    const assertThat: AssertThat =
        assertThatRule(duplicateBlankLineInBlockComment, 'duplicate-blank-line-in-block-comment');

    it('Rule properties', () => assertProperties(duplicateBlankLineInBlockComment));

    it(
        'Single empty line in block comment',
        () =>
            assertThat(
                `
                /**
                 * Lorem ipsum
                 *
                 * dolor sit amet.
                 */
                function foo() {}
                `,
            ).hasNoError(),
    );

    it(
        'Multiple empty lines in block comment',
        () =>
            assertThat(
                `
                /**
                 * Lorem ipsum
                 *
                 *
                 * dolor sit amet.
                 */
                function foo() {}
                `,
            ).hasErrorMessages("Remove consecutive blank line after '*'."),
    );
});
