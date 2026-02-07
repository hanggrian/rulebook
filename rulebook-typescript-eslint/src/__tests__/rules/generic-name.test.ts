import { describe, it } from 'vitest';
import genericName from '../../rules/generic-name';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('GenericNameTest', () => {
    const assertThat: AssertThat = assertThatRule(genericName, 'generic-name');

    it('Rule properties', () => assertProperties(genericName));

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

    it(
        'Incorrect generic name in class',
        () =>
            assertThat(
                `
                class MyClass<XA> {}

                interface MyInterface<Xa> {}

                type MyType<aX> = { val: aX };
                `,
            ).hasErrorMessages(
                'Use single uppercase letter.',
                'Use single uppercase letter.',
                'Use single uppercase letter.',
            ),
    );

    it(
        'Correct generic name in function',
        () =>
            assertThat(
                `
                function execute<E>(list: E[]) {}

                const execute2 = <T>(item: T) => item;
                `,
            ).hasNoError(),
    );

    it(
        'Incorrect generic name in function',
        () =>
            assertThat(
                `
                function execute<Xa>(list: Xa[]) {}

                const execute2 = <aX>(item: aX) => item;
                `,
            ).hasErrorMessages(
                'Use single uppercase letter.',
                'Use single uppercase letter.',
            ),
    );

    it(
        'Skip multiple generics',
        () =>
            assertThat(
                `
                class Foo<Foo1, Foo2> {}

                function bar<Bar1, Bar2> () {}
                `,
            ).hasNoError(),
    );

    it(
        'Skip inner generics',
        () =>
            assertThat(
                `
                class Foo<T> {
                    static Bar = class<XX> {}

                    baz<YY>() {}
                }
                `,
            ).hasNoError(),
    );
});
