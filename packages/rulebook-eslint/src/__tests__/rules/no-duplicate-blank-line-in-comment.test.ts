import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import noDuplicateBlankLineInComment from '../../rules/no-duplicate-blank-line-in-comment';
import assertProperties from '../asserts';

describe('NoDuplicateBlankLineInCommentRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(noDuplicateBlankLineInComment);

    it('Rule properties', () => assertProperties(noDuplicateBlankLineInComment));

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
            ).hasErrorMessages("5:21 Remove consecutive blank line after '//'."),
    );
});
