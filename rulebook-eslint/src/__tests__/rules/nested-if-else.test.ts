import { describe, it } from 'vitest';
import nestedIfElseRule from '../../rules/nested-if-else';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('NestedIfElseRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(nestedIfElseRule, 'nested-if-else');

    it('Rule properties', () => assertProperties(nestedIfElseRule));

    it(
        'Empty or one statement in if statement',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                    }
                }

                function bar() {
                    if (true) {
                        baz();
                    }
                }
                `,
            ).hasNoError(),
    );

    it(
        'Invert if with multiline statement or two statements',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        baz();
                        baz();
                    }
                }

                function bar() {
                    if (true) {
                        baz(
                            0,
                        );
                    }
                }
                `,
            ).hasErrorMessages(
                "Invert 'if' condition.",
                "Invert 'if' condition.",
            ),
    );

    it(
        'Lift else when there is no else if',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        baz();
                    } else {
                        baz();
                        baz();
                    }
                }
                `,
            ).hasErrorMessages("Lift 'else' and add 'return' in 'if' block."),
    );

    it(
        'Skip else if',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        baz();
                        baz();
                    } else if (false) {
                        baz();
                    }
                }
                `,
            ).hasNoError(),
    );

    it(
        'Skip block with jump statement',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        baz();
                        return;
                    }
                }
                `,
            ).hasNoError(),
    );

    it(
        'Capture trailing non-ifs',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        baz();
                        baz();
                    }

                    // Lorem ipsum.
                }
                `,
            ).hasErrorMessages("Invert 'if' condition."),
    );

    it(
        'Skip recursive if-else because it is not safe to return in inner blocks',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        if (true) {
                            baz();
                            baz();
                        }
                    }
                    baz();
                }

                function bar() {
                    if (true) {
                        try {
                            if (true) {
                                baz();
                                baz();
                            }
                        } catch (e) {
                            try {
                                if (true) {
                                    baz();
                                    baz();
                                }
                            } catch (e) {
                                if (true) {
                                    baz();
                                    baz();
                                }
                            }
                        }
                    }
                    baz()
                }
                `,
            ).hasNoError(),
    );

    it(
        "Skip initializer because you can't return in init block",
        () =>
            assertThat(
                `
                class Foo {
                    constructor() {
                        if (true) {
                            baz();
                            baz();
                        }
                    }
                }

                class Bar {
                    constructor() {
                        while (true) {
                            if (true) {
                                baz();
                                baz();
                            }
                        }
                    }
                }
                `,
            ).hasNoError(),
    );
});
