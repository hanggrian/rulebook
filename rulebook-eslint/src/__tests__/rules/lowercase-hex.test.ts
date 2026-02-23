import { describe, it } from 'vitest';
import lowercaseHexRule from '../../rules/lowercase-hex';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('LowercaseHexRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(lowercaseHexRule, 'lowercase-hex');

    it('Rule properties', () => assertProperties(lowercaseHexRule));

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
