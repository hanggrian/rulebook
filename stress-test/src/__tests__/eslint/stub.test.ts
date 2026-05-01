import { type AssertThat } from 'testing/dist/asserters';
import { describe, it } from 'vitest';
import assertThatAllRules from './all-rules-tests';

describe('StubTest', () => {
    const assertThat: AssertThat = assertThatAllRules();

    it(
        'Single-type assignment',
        () =>
            assertThat(
                `function foo() {
                    const bar = 1 + 2;
                }`,
            ).hasNoError(),
    );
});
