import { type AssertThat, assertThatRule } from 'testing/dist/asserters';
import { describe, it } from 'vitest';
import emptyFileRule from '../../rules/empty-file';
import assertProperties from '../asserts';

describe('EmptyFileRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(emptyFileRule, 'empty-file');

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
                .hasErrorMessages('Delete the empty file.'),
    );

    it(
        'Long empty file',
        () =>
            assertThat(
                `

                `,
            ).hasErrorMessages('Delete the empty file.'),
    );
});
