import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import noEmptyFileRule from '../../rules/no-empty-file';
import assertProperties from '../asserts';

describe('NoEmptyFileRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(noEmptyFileRule);

    it('Rule properties', () => assertProperties(noEmptyFileRule));

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
