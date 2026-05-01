import { type AssertThat } from 'testing/dist/typescript-asserters';
import { describe, it } from 'vitest';
import assertThatAllRules from './all-rules-tests';

describe('StubTest', () => {
    const assertThat: AssertThat = assertThatAllRules();

    it(
        'Correct generic name in class',
        () =>
            assertThat(
                `class MyClass<T> {}

                interface MyInterface<V> {}

                type MyType<X> = { val: X };`,
            ).hasNoError(),
    );
});
