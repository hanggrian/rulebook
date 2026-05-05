import { type AssertThat } from 'testing/src/asserters';
import { describe, it } from 'vitest';
import getCode from '../code';
import assertThatAllRules from './all-rules-tests';

describe('SinonTest', () => {
    const assertThat: AssertThat = assertThatAllRules();

    const assertFile: string = 'assert.js';
    const mockExpectationFile: string = 'mock-expectation.js';
    const sandboxFile: string = 'sandbox.js';

    it(
        `Test ${assertFile}`,
        () =>
            assertThat(getCode(`sinon/${assertFile}`))
                .withFilename(assertFile)
                .hasErrorMessages(
                    "141:20 Lift 'else' and add 'return' in 'if' block.",
                    "163:20 Lift 'else' and add 'return' in 'if' block.",
                    '177:29 Add blank line after multiline branch.',
                    '178:26 Add blank line after multiline branch.',
                    '179:30 Add blank line after multiline branch.',
                    '180:31 Add blank line after multiline branch.',
                    '189:22 Add blank line after multiline branch.',
                ),
    );

    it(
        `Test ${mockExpectationFile}`,
        () =>
            assertThat(getCode(`sinon/${mockExpectationFile}`))
                .withFilename(mockExpectationFile)
                .hasErrorMessages(
                    "205:17 Invert 'if' condition.",
                    '232:9 Unused eslint-disable directive ' +
                    "(no problems were reported from 'no-underscore-dangle').",
                    "251:13 Omit redundant 'if' condition.",
                    '282:25 Break assignment into newline.',
                    '287:25 Break assignment into newline.',
                ),
    );

    it(
        `Test ${sandboxFile}`,
        () =>
            assertThat(getCode(`sinon/${sandboxFile}`))
                .withFilename(sandboxFile)
                .hasErrorMessages(
                    '31:27 Break assignment into newline.',
                    "93:9 Invert 'if' condition.",
                    '200:24 Break assignment into newline.',
                    '221:24 Break assignment into newline.',
                    '471:9 Unused eslint-disable directive ' +
                    "(no problems were reported from 'accessor-pairs').",
                ),
    );
});
