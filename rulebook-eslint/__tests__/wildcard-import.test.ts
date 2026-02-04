import { describe, it } from 'vitest';
import wildcardImport from '../src/rules/wildcard-import';
import { AssertThat, assertThatRule } from './tests';

describe('WildcardImportTest', () => {
    const assertThat: AssertThat = assertThatRule(wildcardImport, 'wildcard-import');

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
