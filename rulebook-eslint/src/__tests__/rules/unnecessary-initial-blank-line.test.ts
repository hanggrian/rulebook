import { type AssertThat, assertThatRule } from 'testing/dist/asserters';
import { describe, it } from 'vitest';
import unnecessaryInitialBlankLineRule from '../../rules/unnecessary-initial-blank-line';
import assertProperties from '../asserts';

describe('UnnecessaryInitialBlankLineTest', () => {
    const assertThat: AssertThat =
        assertThatRule(unnecessaryInitialBlankLineRule, 'unnecessary-initial-blank-line');

    it('Rule properties', () => assertProperties(unnecessaryInitialBlankLineRule));

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
            ).hasErrorMessages('Remove blank line at the beginning.'),
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
