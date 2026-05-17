import { type AssertThat } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import getCode from '../code';
import assertThatAllRules from './all-rules-tests';

describe('EslintTest', () => {
    const assertThat: AssertThat = assertThatAllRules();

    const eslintFile: string = 'eslint.js';
    const eslintHelperFile: string = 'eslint-helper.js';
    const linterFile: string = 'linter.js';

    it(
        `Test ${eslintFile}`,
        () =>
            assertThat(getCode(`eslint/${eslintFile}`))
                .withFilename(eslintFile)
                .hasErrorMessages(
                    "52:16 Put newline before '.'.",
                    '121:21 Break assignment into newline.',
                    "128:5 Invert 'if' condition.",
                    '175:22 Break assignment into newline.',
                ),
    );

    it(
        `Test ${eslintHelperFile}`,
        () =>
            assertThat(getCode(`eslint/${eslintHelperFile}`))
                .withFilename(eslintHelperFile)
                .hasErrorMessages(
                    "6:1 Avoid meaningless word 'Helper'.",
                    '68:15 Break assignment into newline.',
                    '285:22 Break assignment into newline.',
                    '303:18 Break assignment into newline.',
                    '412:29 Break assignment into newline.',
                    '449:32 Break assignment into newline.',
                    '457:21 Break assignment into newline.',
                    '529:22 Break assignment into newline.',
                    '571:19 Break assignment into newline.',
                    '680:18 Add blank line after multiline branch.',
                    '684:18 Add blank line after multiline branch.',
                    "685:9 Omit redundant 'default' condition.",
                    '1155:41 Break assignment into newline.',
                    '1254:30 Break assignment into newline.',
                    '1281:22 Break assignment into newline.',
                    '1310:38 Break assignment into newline.',
                ),
    );

    it(
        `Test ${linterFile}`,
        () =>
            assertThat(getCode(`eslint/${linterFile}`))
                .withFilename(linterFile)
                .hasErrorMessages(
                    "68:1 Definition for rule 'jsdoc/valid-types' was not found.",
                    "73:1 Definition for rule 'jsdoc/valid-types' was not found.",
                    '169:28 Break assignment into newline.',
                    '180:31 Break assignment into newline.',
                    "202:5 Invert 'if' condition.",
                    '249:42 Break assignment into newline.',
                    '263:42 Break assignment into newline.',
                    '311:32 Break assignment into newline.',
                    '356:40 Break assignment into newline.',
                    "430:5 Invert 'if' condition.",
                    "434:12 Lift 'else' and add 'return' in 'if' block.",
                    '546:25 Break assignment into newline.',
                    '576:29 Break assignment into newline.',
                    '583:33 Break assignment into newline.',
                    '625:31 Break assignment into newline.',
                    '647:48 Break assignment into newline.',
                    '721:5 Unused eslint-disable directive ' +
                    "(no problems were reported from 'no-undefined').",
                    '889:22 Break assignment into newline.',
                    '898:34 Break assignment into newline.',
                    '915:30 Break assignment into newline.',
                    "1007:13 Omit separator ':'.",
                    '1032:24 Break assignment into newline.',
                    '1159:59 Break assignment into newline.',
                    '1221:51 Break assignment into newline.',
                    "1221:54 Put newline before '.'.",
                    "1227:37 Omit newline before '.'.",
                    '1255:33 Break assignment into newline.',
                    '1331:26 Break assignment into newline.',
                    '1345:22 Break assignment into newline.',
                ),
    );
});
