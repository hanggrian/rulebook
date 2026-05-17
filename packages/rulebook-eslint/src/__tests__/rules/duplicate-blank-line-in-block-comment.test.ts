import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import duplicateBlankLineInBlockCommentRule from '../../rules/duplicate-blank-line-in-block-comment';
import assertProperties from '../asserts';

describe('DuplicateBlankLineInBlockCommentRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(duplicateBlankLineInBlockCommentRule);

    it('Rule properties', () => assertProperties(duplicateBlankLineInBlockCommentRule));

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
            ).hasErrorMessages("5:1 Remove consecutive blank line after '*'."),
    );
});
