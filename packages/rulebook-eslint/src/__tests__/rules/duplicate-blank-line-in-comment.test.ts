import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import duplicateBlankLineInComment from '../../rules/duplicate-blank-line-in-comment';
import assertProperties from '../asserts';

describe('DuplicateBlankLineInCommentRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(duplicateBlankLineInComment);

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
            ).hasErrorMessages("5:21 Remove consecutive blank line after '//'"),
    );
});
