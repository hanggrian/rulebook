import { describe, it } from 'vitest';
import abstractClassDefinition from '../src/rules/abstract-class-definition';
import { assertProperties, AssertThat, assertThatRule } from './tests';

describe('AbstractClassDefinitionTest', () => {
    const assertThat: AssertThat =
        assertThatRule(abstractClassDefinition, 'abstract-class-definition');

    it('Rule properties', () => assertProperties(abstractClassDefinition));

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
