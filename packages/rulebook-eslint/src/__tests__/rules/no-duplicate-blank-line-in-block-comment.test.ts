import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import noDuplicateBlankLineInBlockCommentRule from '../../rules/no-duplicate-blank-line-in-block-comment';
import assertProperties from '../asserts';

describe('NoDuplicateBlankLineInBlockCommentRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(noDuplicateBlankLineInBlockCommentRule);

    it('Rule properties', () => assertProperties(noDuplicateBlankLineInBlockCommentRule));

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
