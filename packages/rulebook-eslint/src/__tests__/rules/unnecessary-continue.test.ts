import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import unnecessaryContinueRule from '../../rules/unnecessary-continue';
import assertProperties from '../asserts';

describe('UnnecessaryContinueRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(unnecessaryContinueRule);

    it('Rule properties', () => assertProperties(unnecessaryContinueRule));

    it(
        "Loops don't end with continue",
        () =>
            assertThat(
                `
                function foo(items) {
                    for (let i = 0; i < 10; i++) {
                        console.log(item);
                    }
                }

                function bar(items) {
                    for (const item in items) {
                        console.log(item);
                    }
                }

                function baz(items) {
                    for (const item of items) {
                        console.log(item);
                    }
                }

                function qux(items) {
                    while (true) {
                        console.log(item);
                    }
                }

                function qux2(items) {
                    do {
                        console.log(item);
                    } while (true)
                }
                `,
            ).hasNoError(),
    );

    it(
        'Loops end with continue',
        () =>
            assertThat(
                `
                function foo(items) {
                    for (let i = 0; i < 10; i++) {
                        console.log(item);
                        continue;
                    }
                }

                function bar(items) {
                    for (const item in items) {
                        console.log(item);
                        continue;
                    }
                }

                function baz(items) {
                    for (const item of items) {
                        console.log(item);
                        continue;
                    }
                }

                function qux(items) {
                    while (true) {
                        console.log(item);
                        continue;
                    }
                }

                function qux2(items) {
                    do {
                        console.log(item);
                        continue;
                    } while (true)
                }
                `,
            ).hasErrorMessages(
                "5:25 Last 'continue' is not needed.",
                "12:25 Last 'continue' is not needed.",
                "19:25 Last 'continue' is not needed.",
                "26:25 Last 'continue' is not needed.",
                "33:25 Last 'continue' is not needed.",
            ),
    );

    it(
        'Capture loops without block',
        () =>
            assertThat(
                `
                function foo(items) {
                    for (let i = 0; i < 10; i++) continue;
                }

                function bar(items) {
                    for (const item in items) continue;
                }

                function baz(items) {
                    for (const item of items) continue;
                }

                function qux(items) {
                    while (true) continue;
                }

                function qux2(items) {
                    do continue; while (true);
                }
                `,
            ).hasErrorMessages(
                "3:50 Last 'continue' is not needed.",
                "7:47 Last 'continue' is not needed.",
                "11:47 Last 'continue' is not needed.",
                "15:34 Last 'continue' is not needed.",
                "19:24 Last 'continue' is not needed.",
            ),
    );

    it(
        'Capture trailing non-continue',
        () =>
            assertThat(
                `
                function foo(items) {
                    for (let i = 0; i < 10; i++) {
                        console.log(item);
                        continue;

                        // Lorem ipsum.
                    }
                }

                function bar(items) {
                    for (const item in items) {
                        console.log(item);
                        continue;

                        // Lorem ipsum.
                    }
                }

                function baz(items) {
                    for (const item of items) {
                        console.log(item);
                        continue;

                        // Lorem ipsum.
                    }
                }

                function qux(items) {
                    while (true) {
                        console.log(item);
                        continue;

                        // Lorem ipsum.
                    }
                }

                function qux2(items) {
                    do {
                        console.log(item);
                        continue;

                        // Lorem ipsum.
                    } while (true)
                }
                `,
            ).hasErrorMessages(
                "5:25 Last 'continue' is not needed.",
                "14:25 Last 'continue' is not needed.",
                "23:25 Last 'continue' is not needed.",
                "32:25 Last 'continue' is not needed.",
                "41:25 Last 'continue' is not needed.",
            ),
    );
});
