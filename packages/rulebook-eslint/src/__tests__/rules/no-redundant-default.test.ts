import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import noRedundantDefaultRule from '../../rules/no-redundant-default';
import assertProperties from '../asserts';

describe('NoRedundantDefaultRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(noRedundantDefaultRule);

    it('Rule properties', () => assertProperties(noRedundantDefaultRule));

    it(
        'No throw or return in case',
        () =>
            assertThat(
                `
                function foo() {
                    switch (bar) {
                        case 0: baz();
                        case 1: baz();
                        default: baz();
                    }
                }
                `,
            ).hasNoError(),
    );

    it(
        'Lift else when case has return',
        () =>
            assertThat(
                `
                function foo() {
                    switch (bar) {
                        case 0: throw Error();
                        case 1: return;
                        default: baz();
                    }
                }
                `,
            ).hasErrorMessages("6:25 Omit redundant 'default' condition."),
    );

    it(
        'Skip if not all case blocks have jump statement',
        () =>
            assertThat(
                `
                function foo() {
                    switch (bar) {
                        case 0: throw Error();
                        case 1: baz();
                        default: baz();
                    }
                }
                `,
            ).hasNoError(),
    );
});
