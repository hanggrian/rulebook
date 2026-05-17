import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import chainCallWrapRule from '../../rules/chain-call-wrap';
import assertProperties from '../asserts';

describe('ChainCallWrapRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(chainCallWrapRule);

    it('Rule properties', () => assertProperties(chainCallWrapRule));

    it(
        'Aligned chain method call continuation',
        () =>
            assertThat(
                `
                function foo() {
                    new StringBuilder(
                        'Lorem ipsum',
                    ).append(0)
                        .append(1);
                }

                function baz() {
                    new Bar()
                        .baz(
                            new String('Lorem ipsum'),
                        ).baz(
                            new String('Lorem ipsum'),
                        );
                }

                class Baz {
                    baz(s) {
                        return this;
                    }

                    baz() {
                        return this;
                    }
                }
                `,
            ).hasNoError(),
    );

    it(
        'Misaligned chain method call continuation',
        () =>
            assertThat(
                `
                function foo() {
                    new StringBuilder(
                        'Lorem ipsum',
                    )
                        .append(0)
                        .append(2);
                }

                function bar() {
                    new Bar()
                        .baz(
                            new String('Lorem ipsum'),
                        )
                        .baz(
                            new String('Lorem ipsum'),
                        );
                }

                class Baz {
                    baz() {
                        return this;
                    }
                }
                `,
            ).hasErrorMessages(
                "6:25 Omit newline before '.'.",
                "15:25 Omit newline before '.'.",
            ),
    );

    it(
        'Skip if all dots are in one line',
        () =>
            assertThat(
                `
                function foo() {
                    new StringBuilder(
                        'Lorem ipsum'
                    ).append(0).append(2);
                }

                function bar() {
                    new Baz(0).baz(0).baz(
                        1
                    );
                }

                class Baz {
                    baz(qux) {
                        return this;
                    }
                }
                `,
            ).hasNoError(),
    );

    it(
        'Also capture non-method call',
        () =>
            assertThat(
                `
                function foo() {
                    new Baz()
                        .baz().baz
                        .baz()
                }

                class Baz {
                    baz = this;

                    baz() {
                        return this;
                    }
                }
                `,
            ).hasErrorMessages("4:31 Put newline before '.'."),
    );

    it(
        'Allow single call',
        () =>
            assertThat(
                `
                function foo(r) {
                    new ChainCallWrapCheck().foo(() => {
                        println();
                        println();
                    });
                }
                `,
            ).hasNoError(),
    );
});
