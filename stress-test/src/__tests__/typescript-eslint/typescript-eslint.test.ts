import { type AssertThat } from 'testing/src/typescript-asserters';
import { describe, it } from 'vitest';
import getCode from '../code';
import assertThatAllRules from './all-rules-tests';

describe('TypescriptEslintTest', () => {
    const assertThat: AssertThat = assertThatAllRules();

    const configHelperFile: string = 'config-helper.ts';
    const convertFile: string = 'convert.ts';
    const parserFile: string = 'parser.ts';

    it(
        `Test ${configHelperFile}`,
        () =>
            assertThat(getCode(`typescript-eslint/${configHelperFile}`))
                .withFilename(configHelperFile)
                .hasErrorMessages(
                    "19:1 Avoid meaningless word 'Helper'.",
                    '121:28 Break assignment into newline.',
                    '135:26 Break assignment into newline.',
                    '199:35 Break assignment into newline.',
                    '205:44 Break assignment into newline.',
                ),
    );

    it(
        `Test ${convertFile}`,
        () =>
            assertThat(getCode(`typescript-eslint/${convertFile}`))
                .withFilename(convertFile)
                .hasErrorMessages(
                    "2:1 Definition for rule '@typescript-eslint/no-non-null-assertion' " +
                    'was not found.',
                    "2:1 Definition for rule '@typescript-eslint/no-unnecessary-condition' " +
                    'was not found.',
                    "2:1 Definition for rule '@typescript-eslint/no-explicit-any' " +
                    'was not found.',
                    "2:1 Definition for rule '@typescript-eslint/no-unsafe-assignment' " +
                    'was not found.',
                    "2:1 Definition for rule '@typescript-eslint/no-unsafe-call' " +
                    'was not found.',
                    "2:1 Definition for rule '@typescript-eslint/no-unsafe-return' " +
                    'was not found.',
                    "2:1 Definition for rule '@typescript-eslint/no-unsafe-member-access' " +
                    'was not found.',
                    "253:17 Put newline before '.'.",
                    '265:39 Break assignment into newline.',
                    "501:9 Definition for rule '@typescript-eslint/no-deprecated' was not found.",
                    '509:24 Break assignment into newline.',
                    '523:28 Break assignment into newline.',
                    '544:28 Break assignment into newline.',
                    '590:40 Add blank line after multiline branch.',
                    '591:39 Add blank line after multiline branch.',
                    "645:9 Invert 'if' condition.",
                    '753:39 Add blank line after multiline branch.',
                    '847:32 Break assignment into newline.',
                    '871:28 Break assignment into newline.',
                    '886:32 Break assignment into newline.',
                    '1013:30 Break assignment into newline.',
                    '1058:40 Add blank line after multiline branch.',
                    '1066:13 Add blank line after multiline branch.',
                    '1069:32 Break assignment into newline.',
                    '1122:50 Break assignment into newline.',
                    '1164:37 Break assignment into newline.',
                    '1362:33 Break assignment into newline.',
                    '1388:32 Break assignment into newline.',
                    '1414:41 Add blank line after multiline branch.',
                    '1415:43 Add blank line after multiline branch.',
                    '1418:33 Break assignment into newline.',
                    '1441:45 Add blank line after multiline branch.',
                    '1530:45 Add blank line after multiline branch.',
                    '1547:32 Break assignment into newline.',
                    '1587:32 Break assignment into newline.',
                    "1604:21 Omit separator '('.",
                    "1606:21 Definition for rule '@typescript-eslint/no-deprecated' was not found.",
                    '1625:38 Add blank line after multiline branch.',
                    '1729:50 Add blank line after multiline branch.',
                    '1787:36 Break assignment into newline.',
                    '1841:32 Break assignment into newline.',
                    '1857:32 Break assignment into newline.',
                    '1891:32 Break assignment into newline.',
                    "1926:25 Omit separator ':'.",
                    '1971:32 Break assignment into newline.',
                    "1973:21 Put newline before '.'.",
                    "1976:21 Put newline before '.'.",
                    '2106:36 Break assignment into newline.',
                    '2186:39 Add blank line after multiline branch.',
                    '2187:42 Add blank line after multiline branch.',
                    '2188:43 Add blank line after multiline branch.',
                    '2189:41 Add blank line after multiline branch.',
                    '2190:42 Add blank line after multiline branch.',
                    '2191:42 Add blank line after multiline branch.',
                    '2192:42 Add blank line after multiline branch.',
                    '2193:42 Add blank line after multiline branch.',
                    '2194:43 Add blank line after multiline branch.',
                    '2195:40 Add blank line after multiline branch.',
                    '2196:45 Add blank line after multiline branch.',
                    '2204:32 Break assignment into newline.',
                    '2281:32 Break assignment into newline.',
                    '2332:41 Add blank line after multiline branch.',
                    '2333:47 Add blank line after multiline branch.',
                    '2392:32 Break assignment into newline.',
                    '2409:32 Break assignment into newline.',
                    '2436:35 Break assignment into newline.',
                    '2457:50 Break assignment into newline.',
                    '2467:47 Break assignment into newline.',
                    '2507:32 Break assignment into newline.',
                    '2536:32 Break assignment into newline.',
                    '2580:32 Break assignment into newline.',
                    '2596:73 Break assignment into newline.',
                    '2632:43 Break assignment into newline.',
                    "2663:21 Definition for rule '@typescript-eslint/no-deprecated' was not found.",
                    '2673:13 Add blank line after multiline branch.',
                    '2679:13 Add blank line after multiline branch.',
                    '2685:13 Add blank line after multiline branch.',
                    '2692:13 Add blank line after multiline branch.',
                    '2698:13 Add blank line after multiline branch.',
                    '2712:13 Add blank line after multiline branch.',
                    '2719:13 Add blank line after multiline branch.',
                    '2730:13 Add blank line after multiline branch.',
                    '2736:13 Add blank line after multiline branch.',
                    '2742:13 Add blank line after multiline branch.',
                    '2757:13 Add blank line after multiline branch.',
                    '2759:32 Break assignment into newline.',
                    '2777:13 Add blank line after multiline branch.',
                    '2783:13 Add blank line after multiline branch.',
                    '2793:32 Break assignment into newline.',
                    "2817:13 Definition for rule '@typescript-eslint/no-deprecated' was not found.",
                    '2818:40 Add blank line after multiline branch.',
                    '2881:24 Break assignment into newline.',
                    '2911:34 Break assignment into newline.',
                    "2934:15 Put newline before '.'.",
                    '2973:27 Break assignment into newline.',
                    '2988:30 Break assignment into newline.',
                    "3009:17 Omit separator ','.",
                ),
    );

    it(
        `Test ${parserFile}`,
        () =>
            assertThat(getCode(`typescript-eslint/${parserFile}`))
                .withFilename(parserFile)
                .hasErrorMessages(
                    "51:31 Put newline before '.'.",
                    '56:27 Break assignment into newline.',
                    "58:29 Definition for rule '@typescript-eslint/no-deprecated' was not found.",
                    '64:27 Add blank line after multiline branch.',
                    '66:35 Add blank line after multiline branch.',
                    '68:35 Add blank line after multiline branch.',
                    '70:35 Add blank line after multiline branch.',
                    '72:35 Add blank line after multiline branch.',
                    '74:35 Add blank line after multiline branch.',
                    '76:35 Add blank line after multiline branch.',
                    '78:35 Add blank line after multiline branch.',
                    '80:35 Add blank line after multiline branch.',
                    '82:35 Add blank line after multiline branch.',
                    '84:35 Add blank line after multiline branch.',
                    '86:35 Add blank line after multiline branch.',
                    "87:9 Omit redundant 'default' condition.",
                    '124:48 Break assignment into newline.',
                    '129:29 Break assignment into newline.',
                    "163:13 Definition for rule '@typescript-eslint/internal/eqeq-nullish' " +
                    'was not found.',
                    "173:13 Definition for rule '@typescript-eslint/internal/eqeq-nullish' " +
                    'was not found.',
                    '178:33 Break assignment into newline.',
                    "178:48 Put newline before '.'.",
                ),
    );
});
