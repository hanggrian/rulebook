import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import bracesClippingRule from '../../rules/braces-clipping';
import assertProperties from '../asserts';

describe('BracesClippingRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(bracesClippingRule);

    it('Rule properties', () => assertProperties(bracesClippingRule));

    it(
        'Wrapped braces',
        () =>
            assertThat(
                `
                class Foo {}

                function bar() {}
                `,
            ).hasNoError(),
    );

    it(
        'Unwrapped braces',
        () =>
            assertThat(
                `
                class Foo { }

                function bar() {
                }
                `,
            ).hasErrorMessages(
                "2:27 Convert into '{}'.",
                "4:32 Convert into '{}'.",
            ),
    );

    it(
        'Allow braces with comment',
        () =>
            assertThat(
                `
                class Foo {
                    // Lorem ipsum.
                }
                `,
            ).hasNoError(),
    );

    it(
        'Control flows with multi-blocks',
        () =>
            assertThat(
                `
                function foo() {
                    try {
                    } catch (e) {
                    }

                    if (true) {
                    } else if (false) {
                    } else {
                    }

                    do {
                    } while (true)
                }
                `,
            ).hasNoError(),
    );
});
