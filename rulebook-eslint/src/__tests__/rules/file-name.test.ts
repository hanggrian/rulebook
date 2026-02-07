import { describe, it } from 'vitest';
import fileName from '../../rules/file-name';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('FileNameTest', () => {
    const assertThat: AssertThat = assertThatRule(fileName, 'file-name');

    it('Rule properties', () => assertProperties(fileName));

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
