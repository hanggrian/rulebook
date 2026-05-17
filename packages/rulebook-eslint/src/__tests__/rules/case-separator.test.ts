import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import caseSeparatorRule from '../../rules/case-separator';
import assertProperties from '../asserts';

describe('CaseSeparatorRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(caseSeparatorRule);

    it('Rule properties', () => assertProperties(caseSeparatorRule));

    it(
        'Single-line branches without line break',
        () =>
            assertThat(
                `
                switch (bar) {
                    case 0: baz();
                    case 1: baz(); break;
                    default: baz();
                }
                `,
            ).hasNoError(),
    );

    it(
        'Multiline branches with line break',
        () =>
            assertThat(
                `
                switch (bar) {
                    case 0:
                        baz();
                        break;

                    case 1:
                        baz();
                        break;

                    default:
                        baz();
                }
                `,
            ).hasNoError(),
    );

    it(
        'Single-line branches with line break',
        () =>
            assertThat(
                `
                switch (bar) {
                    case 0: baz();

                    case 1: baz(); break;

                    default: baz();
                }
                `,
            ).hasErrorMessages(
                '3:34 Remove blank line after single-line branch.',
                '5:41 Remove blank line after single-line branch.',
            ),
    );

    it(
        'Multiline branches without line break',
        () =>
            assertThat(
                `
                switch (bar) {
                    case 0:
                        baz();
                    case 1:
                        baz();
                        break;
                    default:
                        baz();
                }
                `,
            ).hasErrorMessages(
                '4:30 Add blank line after multiline branch.',
                '7:30 Add blank line after multiline branch.',
            ),
    );

    it(
        'Branches with comment are always multiline',
        () =>
            assertThat(
                `
                switch (bar) {
                    // Lorem ipsum.
                    case 0:
                        baz();
                    /* Lorem ipsum. */
                    case 1:
                        baz();
                    /** Lorem ipsum. */
                    case 2:
                        baz();
                    default:
                        baz();
                }
                `,
            ).hasErrorMessages(
                '5:30 Add blank line after multiline branch.',
                '8:30 Add blank line after multiline branch.',
                '11:30 Add blank line after multiline branch.',
            ),
    );
});
