import { describe, it } from 'vitest';
import caseSeparator from '../src/rules/case-separator';
import { assertProperties, AssertThat, assertThatRule } from './tests';

describe('CaseSeparatorTest', () => {
    const assertThat: AssertThat = assertThatRule(caseSeparator, 'case-separator');

    it('Rule properties', () => assertProperties(caseSeparator));

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
                'Remove blank line after single-line branch.',
                'Remove blank line after single-line branch.',
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
                'Add blank line after multiline branch.',
                'Add blank line after multiline branch.',
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
                'Add blank line after multiline branch.',
                'Add blank line after multiline branch.',
                'Add blank line after multiline branch.',
            ),
    );
});
