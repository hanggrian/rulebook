import { describe, it } from 'vitest';
import assignmentWrap from '../../rules/assignment-wrap';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('AssignmentWrapTest', () => {
    const assertThat: AssertThat = assertThatRule(assignmentWrap, 'assignment-wrap');

    it('Rule properties', () => assertProperties(assignmentWrap));

    it(
        'Single-type assignment',
        () =>
            assertThat(
                `
                function foo() {
                    const bar = 1 + 2;
                }
                `,
            ).hasNoError(),
    );

    it(
        'Multiline assignment with breaking assignee',
        () =>
            assertThat(
                `
                function foo() {
                    const bar =
                        1 +
                        2;
                }
                `,
            ).hasNoError(),
    );

    it(
        'Multiline assignment with non-breaking assignee',
        () =>
            assertThat(
                `
                function foo() {
                    const bar = 1 +
                        2;
                }
                `,
            ).hasErrorMessages('Break assignment into newline.'),
    );

    it(
        'Multiline variable but single-line value',
        () =>
            assertThat(
                `
                function foo(bar) {
                    bar
                        .baz = 1;
                }
                `,
            ).hasNoError(),
    );

    it(
        'Allow comments after assign operator',
        () =>
            assertThat(
                `
                function foo() {
                    const bar =
                        // Comment
                        1 +
                        2;
                    const baz = /* Short comment */
                        1 +
                        2;
                    const quz =
                        /** Long comment */1 +
                        2;
                }
                `,
            ).hasNoError(),
    );

    it(
        'Skip lambda initializers',
        () =>
            assertThat(
                `
                function foo() {
                    const bar = (a) => {
                        console.log(a);
                    };

                    const bar2 = (a) => console.log(a);
                }
                `,
            ).hasNoError(),
    );

    it(
        'Skip collection initializers',
        () =>
            assertThat(
                `
                function foo() {
                    const bar = [
                        1,
                        2,
                    ];
                }
                `,
            ).hasNoError(),
    );
});
