import { describe, it } from 'vitest';
import redundantDefaultRule from '../../rules/redundant-default';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('RedundantDefaultRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(redundantDefaultRule, 'redundant-default');

    it('Rule properties', () => assertProperties(redundantDefaultRule));

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
            ).hasErrorMessages("Omit redundant 'default' condition."),
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
