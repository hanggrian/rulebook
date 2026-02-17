import { describe, it } from 'vitest';
import unnecessaryAbstractClassRule from '../../rules/unnecessary-abstract';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('UnnecessaryAbstractRuleTest', () => {
    const assertThat: AssertThat =
        assertThatRule(unnecessaryAbstractClassRule, 'abstract-class-definition');

    it('Rule properties', () => assertProperties(unnecessaryAbstractClassRule));

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
            ).hasErrorMessages("Omit 'abstract' modifier."),
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
