import { describe, it } from 'vitest';
import unnecessaryInitialBlankLineRule from '../../rules/unnecessary-initial-blank-line';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

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
