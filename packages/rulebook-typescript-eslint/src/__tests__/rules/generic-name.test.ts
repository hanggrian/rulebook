import { type AssertThat, assertThatRule } from 'testing-js/src/typescript-asserters';
import { describe, it } from 'vitest';
import genericNameRule from '../../rules/generic-name';
import assertProperties from '../asserts';

describe('GenericNameRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(genericNameRule);

    it('Rule properties', () => assertProperties(genericNameRule));

    it(
        'Correct generic name in class',
        () =>
            assertThat(
                `
                class MyClass<Type> {}

                interface MyInterface<GenericValue> {}

                type MyType<E> = { val: E };
                `,
            ).hasNoError(),
    );

    it(
        'Incorrect generic name in class',
        () =>
            assertThat(
                `
                class MyClass<TYPE> {}

                interface MyInterface<generic_value> {}

                type MyType<element> = { val: element };
                `,
            ).hasErrorMessages(
                '2:31 Use pascal-case name.',
                '4:39 Use pascal-case name.',
                '6:29 Use pascal-case name.',
            ),
    );

    it(
        'Correct generic name in function',
        () =>
            assertThat(
                `
                function execute<SingleElement>(list: SingleElement[]) {}

                const execute2 = <Type>(item: Type) => item;
                `,
            ).hasNoError(),
    );

    it(
        'Incorrect generic name in function',
        () =>
            assertThat(
                `
                function execute<generic_value>(list: generic_value[]) {}

                const execute2 = <element>(item: element) => item;
                `,
            ).hasErrorMessages(
                '2:34 Use pascal-case name.',
                '4:35 Use pascal-case name.',
            ),
    );
});
