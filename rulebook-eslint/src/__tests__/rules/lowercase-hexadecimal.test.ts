import { type AssertThat, assertThatRule } from 'testing/dist/asserters';
import { describe, it } from 'vitest';
import lowercaseHexadecimalRule from '../../rules/lowercase-hexadecimal';
import assertProperties from '../asserts';

describe('LowercaseHexadecimalRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(lowercaseHexadecimalRule);

    it('Rule properties', () => assertProperties(lowercaseHexadecimalRule));

    it(
        'Lowercase hexadecimal letters',
        () =>
            assertThat(
                `
                const foo = 0x00bb00;

                function bar() {
                    const bar = 0xaa00cc;
                }
                `,
            ).hasNoError(),
    );

    it(
        'Uppercase hexadecimal letters',
        () =>
            assertThat(
                `
                const foo = 0X00BB00;

                function bar() {
                    const bar = 0XAA00CC;
                }
                `,
            ).hasErrorMessages(
                "Use hexadecimal '0x00bb00'.",
                "Use hexadecimal '0xaa00cc'.",
            ),
    );
});
