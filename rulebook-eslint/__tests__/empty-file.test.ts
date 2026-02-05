import { describe, it } from 'vitest';
import emptyFile from '../src/rules/empty-file';
import { assertProperties, AssertThat, assertThatRule } from './tests';

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

                `
            ).hasErrorMessages('Delete the empty file.'),
    );
});
