import { describe, it } from 'vitest';
import wildcardImport from '../../rules/wildcard-import';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('WildcardImportTest', () => {
    const assertThat: AssertThat = assertThatRule(wildcardImport, 'wildcard-import');

    it('Rule properties', () => assertProperties(wildcardImport));

    it(
        'Single-type import',
        () =>
            assertThat("import Foo from 'foo';")
                .hasNoError(),
    );

    it(
        'Wildcard import',
        () =>
            assertThat("import * as Foo from 'foo';")
                .hasErrorMessages('Use single-type import.'),
    );
});
