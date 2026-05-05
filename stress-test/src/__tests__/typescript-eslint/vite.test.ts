import { type AssertThat } from 'testing/src/typescript-asserters';
import { describe, it } from 'vitest';
import getCode from '../code';
import assertThatAllRules from './all-rules-tests';

describe('ViteTest', () => {
    const assertThat: AssertThat = assertThatAllRules();

    const buildFile: string = 'build.ts';
    const clientFile: string = 'client.ts';
    const pluginFile: string = 'plugin.ts';

    it(
        `Test ${buildFile}`,
        () =>
            assertThat(getCode(`vite/${buildFile}`))
                .withFilename(buildFile)
                .hasErrorMessages(
                    '378:42 Break assignment into newline.',
                    '441:20 Break assignment into newline.',
                    '580:19 Break assignment into newline.',
                    '619:21 Break assignment into newline.',
                    "647:9 Omit separator ':'.",
                    '774:21 Break assignment into newline.',
                    '816:37 Break assignment into newline.',
                    '821:33 Break assignment into newline.',
                    '827:45 Break assignment into newline.',
                    '840:29 Break assignment into newline.',
                    "967:12 Omit redundant 'else' condition.",
                    "1068:5 Invert 'if' condition.",
                    '1122:17 Add blank line after multiline branch.',
                    '1125:17 Add blank line after multiline branch.',
                    '1128:17 Add blank line after multiline branch.',
                    '1131:17 Add blank line after multiline branch.',
                    "1132:13 Omit redundant 'default' condition.",
                    "1184:12 Omit redundant 'else' condition.",
                    "1186:12 Omit redundant 'else' condition.",
                    "1194:12 Omit redundant 'else' condition.",
                    '1258:27 Break assignment into newline.',
                    '1271:17 Add blank line after multiline branch.',
                    '1274:17 Add blank line after multiline branch.',
                    '1281:17 Add blank line after multiline branch.',
                    "1282:13 Omit redundant 'default' condition.",
                    '1303:24 Break assignment into newline.',
                    '1352:42 Break assignment into newline.',
                    "1363:12 Omit redundant 'else' condition.",
                    '1376:37 Break assignment into newline.',
                    "1386:12 Omit redundant 'else' condition.",
                    '1399:42 Break assignment into newline.',
                    "1410:12 Omit redundant 'else' condition.",
                    '1427:39 Break assignment into newline.',
                    "1448:12 Omit redundant 'else' condition.",
                    "1467:5 Invert 'if' condition.",
                    '1492:24 Break assignment into newline.',
                    '1586:37 Break assignment into newline.',
                    '1620:24 Break assignment into newline.',
                    '1667:24 Break assignment into newline.',
                    "1688:12 Omit redundant 'else' condition.",
                    '1758:33 Break assignment into newline.',
                    '1808:35 Break assignment into newline.',
                    "1832:13 Invert 'if' condition.",
                    "1851:13 Invert 'if' condition.",
                ),
    );

    it(
        `Test ${buildFile}`,
        () =>
            assertThat(getCode(`vite/${clientFile}`))
                .withFilename(buildFile)
                .hasErrorMessages(
                    '42:20 Break assignment into newline.',
                    '52:19 Break assignment into newline.',
                    '54:27 Break assignment into newline.',
                    '148:19 Break assignment into newline.',
                    '160:35 Break assignment into newline.',
                    '182:35 Break assignment into newline.',
                    '210:13 Add blank line after multiline branch.',
                    "221:24 Omit redundant 'else' condition.",
                    '241:32 Break assignment into newline.',
                    "241:37 Put newline before '.'.",
                    '252:37 Break assignment into newline.',
                    '277:13 Add blank line after multiline branch.',
                    '291:9 Add blank line after multiline branch.',
                    "308:24 Omit redundant 'else' condition.",
                    '312:13 Add blank line after multiline branch.',
                    '316:13 Add blank line after multiline branch.',
                    '330:9 Add blank line after multiline branch.',
                    '332:13 Add blank line after multiline branch.',
                    "333:9 Omit redundant 'default' condition.",
                    "346:5 Invert 'if' condition.",
                    '379:18 Break assignment into newline.',
                    "505:17 Invert 'if' condition.",
                    "546:9 Omit newline before '.'.",
                    "593:13 Omit newline before '.'.",
                    "601:5 Invert 'if' condition.",
                    "637:13 Omit separator ':'.",
                    '658:17 Add blank line after multiline branch.',
                    "659:17 Omit redundant 'default' condition.",
                ),
    );

    it(
        `Test ${pluginFile}`,
        () =>
            assertThat(getCode(`vite/${pluginFile}`))
                .withFilename(pluginFile)
                .hasNoError(),
    );
});
