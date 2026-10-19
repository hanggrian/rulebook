import { type AssertThat, assertThatRule } from 'testing-js/src/typescript-asserters';
import { describe, it } from 'vitest';
import abstractFunctionInAbstractClassRule from '../../rules/abstract-function-in-abstract-class';
import assertProperties from '../asserts';

describe('AbstractFunctionInAbstractClassRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(abstractFunctionInAbstractClassRule);

    it('Rule properties', () => assertProperties(abstractFunctionInAbstractClassRule));

    it(
        'Abstract class with abstract function',
        () =>
            assertThat(
                `
                abstract class Foo {
                    abstract bar(): boolean;
                }
                `,
            ).hasNoError(),
    );

    it(
        'Abstract class without abstract function',
        () =>
            assertThat(
                `
                abstract class Foo {
                    bar(): boolean;
                }
                `,
            ).hasErrorMessages("2:17 Omit 'abstract' modifier."),
    );

    it(
        'Skip class with inheritance',
        () =>
            assertThat(
                `
                abstract class Foo extends Bar {
                    bar(): boolean;
                }

                abstract class Baz implements Qux {
                    bar(): boolean;
                }
                `,
            ).hasNoError(),
    );
});
