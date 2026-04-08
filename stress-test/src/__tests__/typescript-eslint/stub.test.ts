import genericNameRule from 'rulebook-typescript-eslint/dist/rules/generic-name';
import { AssertThat, assertThatRule } from 'testing/dist/typescript-asserters';
import { describe, it } from 'vitest';

describe('StubTest', () => {
    const assertThat: AssertThat = assertThatRule(genericNameRule, 'generic-name');

    it(
        'Correct generic name in class',
        () =>
            assertThat(
                `
                class MyClass<T> {}

                interface MyInterface<V> {}

                type MyType<X> = { val: X };
                `,
            ).hasNoError(),
    );
});
