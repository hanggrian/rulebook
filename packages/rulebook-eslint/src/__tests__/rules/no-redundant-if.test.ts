import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import noRedundantIfRule from '../../rules/no-redundant-if';
import assertProperties from '../asserts';

describe('NoRedundantIfRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(noRedundantIfRule);

    it('Rule properties', () => assertProperties(noRedundantIfRule));

    it(
        'If-else do not contain boolean constants',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        return true;
                    } else {
                        return "Lorem" == "ipsum";
                    }
                }
                `,
            ).hasNoError(),
    );

    it(
        'If-else contain boolean constants',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        return true;
                    } else {
                        return false;
                    }
                }

                function bar() {
                    if (true) {
                        return true;
                    }
                    return false;
                }
                `,
            ).hasErrorMessages(
                "3:21 Omit redundant 'if' condition.",
                "11:21 Omit redundant 'if' condition.",
            ),
    );

    it(
        'Capture conditions without blocks',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) return true;
                    else return false;
                }
                `,
            ).hasErrorMessages("3:21 Omit redundant 'if' condition."),
    );

    it(
        'Capture trailing non-ifs',
        () =>
            assertThat(
                `
                function foo() {
                    if (true) {
                        return true;
                    } else {
                        return false;
                    }

                    // Lorem ipsum.
                }
                `,
            ).hasErrorMessages("3:21 Omit redundant 'if' condition."),
    );
});
