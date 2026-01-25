import { describe, it } from 'vitest';
import wildcardImport, { MSG } from '../rules/wildcard-import';
import messages from '../messages';
import { AssertThat, assertThatRule } from './tests';

describe('WildcardImportTest', () => {
    const assertThat: AssertThat = assertThatRule(wildcardImport, 'wildcard-import');

    it('Single-type import', () =>
        assertThat("import Foo from 'foo';")
            .hasNoError(),
    );

    it('Wildcard import', () =>
        assertThat("import * as Foo from 'foo';")
            .hasErrorMessages(messages.get(MSG)),
    );
});
