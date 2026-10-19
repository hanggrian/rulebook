import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import noRedundantElseRule from '../../rules/no-redundant-else';
import assertProperties from '../asserts';

describe('NoRedundantElseRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(noRedundantElseRule);

    it('Rule properties', () => assertProperties(noRedundantElseRule));

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
                "5:28 Omit redundant 'else' condition.",
                "7:28 Omit redundant 'else' condition.",
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
                "4:26 Omit redundant 'else' condition.",
                "5:26 Omit redundant 'else' condition.",
            ),
    );
});
