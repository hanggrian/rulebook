import { type AssertThat, assertThatRule } from 'testing/src/asserters';
import { describe, it } from 'vitest';
import emptyFileRule from '../../rules/empty-file';
import assertProperties from '../asserts';

describe('EmptyFileRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(emptyFileRule);

    it('Rule properties', () => assertProperties(emptyFileRule));

    it(
        'Non-empty file',
        () =>
            assertThat('// commment')
                .hasNoError(),
    );

    it(
        'Empty file',
        () =>
            assertThat('')
                .hasErrorMessages('1:1 Delete the empty file.'),
    );

    it(
        'Long empty file',
        () =>
            assertThat(
                `

                `,
            ).hasErrorMessages('1:1 Delete the empty file.'),
    );
});
