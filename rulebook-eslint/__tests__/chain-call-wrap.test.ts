import { describe, it } from 'vitest';
import chainCallWrap, { MSG_MISSING, MSG_UNEXPECTED } from '../src/rules/chain-call-wrap';
import messages from '../src/messages';
import { AssertThat, assertThatRule } from './tests';

describe('ChainCallWrapTest', () => {
    const assertThat: AssertThat = assertThatRule(chainCallWrap, 'chain-call-wrap');

    it('Aligned chain method call continuation', () =>
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

    it('Misaligned chain method call continuation', () =>
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
            messages.get(MSG_UNEXPECTED),
            messages.get(MSG_UNEXPECTED),
        ),
    );

    it('Inconsistent dot position', () =>
        assertThat(
            `
            function foo() {
                new StringBuilder(
                    'Lorem ipsum'
                ).append(0).append(2);
            }

            function bar() {
                new Baz().baz()
                    .baz();
            }

            class Baz {
                baz() {
                    return this;
                }
            }
            `,
        ).hasErrorMessages(
            messages.get(MSG_MISSING),
            messages.get(MSG_MISSING),
        ),
    );

    it('Also capture non-method call', () =>
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
        ).hasErrorMessages(messages.get(MSG_MISSING)),
    );

    it('Allow single call', () =>
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
