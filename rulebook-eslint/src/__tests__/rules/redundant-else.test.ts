import { type AssertThat, assertThatRule } from 'testing/dist/asserters';
import { describe, it } from 'vitest';
import redundantElseRule from '../../rules/redundant-else';
import assertProperties from '../asserts';

describe('RedundantElseRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(redundantElseRule);

    it('Rule properties', () => assertProperties(redundantElseRule));

    it(
        'No throw or return in if',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        baz();
                    } else if (false) {
                        baz();
                    } else {
                        baz();
                    }
                }
                `,
            ).hasNoError(),
    );

    it(
        'Lift else when if has return',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        throw new Error();
                    } else if (false) {
                        return;
                    } else {
                        baz();
                    }
                }
                `,
            ).hasErrorMessages(
                "Omit redundant 'else' condition.",
                "Omit redundant 'else' condition.",
            ),
    );

    it(
        'Consider if-else without blocks',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) throw Exception();
                    else if (false) return;
                    else baz();
                }
                `,
            ).hasErrorMessages(
                "Omit redundant 'else' condition.",
                "Omit redundant 'else' condition.",
            ),
    );
});
