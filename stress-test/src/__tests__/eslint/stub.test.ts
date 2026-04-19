import assignmentWrapRule from 'rulebook-eslint/dist/rules/assignment-wrap';
import { type AssertThat, assertThatRule } from 'testing/dist/asserters';
import { describe, it } from 'vitest';

describe('StubTest', () => {
    const assertThat: AssertThat = assertThatRule(assignmentWrapRule, 'assignment-wrap');

    it(
        'Single-type assignment',
        () =>
            assertThat(
                `
                function foo() {
                    const bar = 1 + 2;
                }
                `,
            ).hasNoError(),
    );
});
