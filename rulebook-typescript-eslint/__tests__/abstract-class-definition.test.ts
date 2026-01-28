import { describe, it } from 'vitest';
import abstractClassDefinition, { MSG } from '../src/rules/abstract-class-definition';
import messages from '../src/messages';
import { AssertThat, assertThatRule } from './tests';

describe('AbstractClassDefinitionTest', () => {
    const assertThat: AssertThat =
        assertThatRule(abstractClassDefinition, 'abstract-class-definition');

    it('Abstract class with abstract function', () =>
        assertThat(
            `
            abstract class Foo {
                abstract bar(): boolean;
            }
            `,
        ).hasNoError(),
    );

    it('Abstract class without abstract function', () =>
        assertThat(
            `
            abstract class Foo {
                bar(): boolean;
            }
            `,
        ).hasErrorMessages(messages.get(MSG)),
    );

    it('Skip class with inheritance', () =>
        assertThat(
            `
            abstract class Foo extends Bar {
                bar(): boolean;
            }
            `,
        ).hasNoError(),
    );
});
