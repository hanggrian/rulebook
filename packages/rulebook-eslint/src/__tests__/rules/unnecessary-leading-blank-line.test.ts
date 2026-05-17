import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import unnecessaryLeadingBlankLineRule from '../../rules/unnecessary-leading-blank-line';
import assertProperties from '../asserts';

describe('UnnecessaryInitialBlankLineTest', () => {
    const assertThat: AssertThat = assertThatRule(unnecessaryLeadingBlankLineRule);

    it('Rule properties', () => assertProperties(unnecessaryLeadingBlankLineRule));

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
