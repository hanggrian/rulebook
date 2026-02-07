import { describe, it } from 'vitest';
import emptyFile from '../../rules/empty-file';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('EmptyFileTest', () => {
    const assertThat: AssertThat = assertThatRule(emptyFile, 'empty-file');

    it('Rule properties', () => assertProperties(emptyFile));

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
