import { type AssertThat, assertThatRule } from 'testing/dist/asserters';
import { describe, it } from 'vitest';
import redundantIfRule from '../../rules/redundant-if';
import assertProperties from '../asserts';

describe('RedundantIfRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(redundantIfRule, 'redundant-if');

    it('Rule properties', () => assertProperties(redundantIfRule));

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
                "Omit redundant 'if' condition.",
                "Omit redundant 'if' condition.",
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
            ).hasErrorMessages("Omit redundant 'if' condition."),
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
            ).hasErrorMessages("Omit redundant 'if' condition."),
    );
});
