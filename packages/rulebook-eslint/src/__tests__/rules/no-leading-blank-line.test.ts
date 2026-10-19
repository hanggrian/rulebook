import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import noLeadingBlankLineRule from '../../rules/no-leading-blank-line';
import assertProperties from '../asserts';

describe('NoLeadingBlankLineRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(noLeadingBlankLineRule);

    it('Rule properties', () => assertProperties(noLeadingBlankLineRule));

    it(
        'Trimmed file',
        () => assertThat('class MyClass {}').hasNoError(),
    );

    it(
        'Padded file',
        () =>
            assertThat(
                `
                class MyClass {}
                `,
            ).hasErrorMessages('1:1 Remove blank line at the beginning.'),
    );

    it(
        'Skip comment',
        () =>
            assertThat(
                `// Lorem ipsum.

                class MyClass {}
                `,
            ).hasNoError(),
    );
});
