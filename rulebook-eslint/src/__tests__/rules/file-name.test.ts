import { type AssertThat, assertThatRule } from 'testing/src/asserters';
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
        () => {
            assertThat('')
                .withFilename('helloWorld.js')
                .hasErrorMessages("1:1 Rename file to 'helloworld.js'.");
            assertThat('')
                .withFilename('HELLO_WORLD.js')
                .hasErrorMessages("1:1 Rename file to 'hello-world.js'.");
        },
    );
});
