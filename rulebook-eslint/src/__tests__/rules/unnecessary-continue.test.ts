import { describe, it } from 'vitest';
import unnecessaryContinueRule from '../../rules/unnecessary-continue';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('UnnecessaryContinueRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(unnecessaryContinueRule, 'unnecessary-continue');

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
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
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
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
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
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
                "Last 'continue' is not needed.",
            ),
    );
});
