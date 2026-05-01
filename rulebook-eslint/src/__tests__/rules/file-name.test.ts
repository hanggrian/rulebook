import { type AssertThat, assertThatRule } from 'testing/dist/asserters';
import { describe, it } from 'vitest';
import fileNameRule from '../../rules/file-name';
import assertProperties from '../asserts';

describe('FileNameRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(fileNameRule);

    it('Rule properties', () => assertProperties(fileNameRule));

    it(
        'Correct file name',
        () =>
            assertThat('')
                .withFilename('hello-world.js')
                .hasNoError(),
    );

    it(
        'Incorrect file names',
        async () => {
            await assertThat('')
                .withFilename('helloWorld.js')
                .hasErrorMessages("Rename file to 'helloworld.js'.");
            await assertThat('')
                .withFilename('HELLO_WORLD.js')
                .hasErrorMessages("Rename file to 'hello-world.js'.");
        },
    );
});
