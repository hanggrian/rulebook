import { describe, it } from 'vitest';
import meaninglessWord from '../../rules/meaningless-word';
import { AssertThat, assertProperties, assertThatRule } from '../tests';

describe('MeaninglessWordTest', () => {
    const assertThat: AssertThat = assertThatRule(meaninglessWord, 'meaningless-word');

    it('Rule properties', () => assertProperties(meaninglessWord));

    it(
        'Meaningful class names',
        () =>
            assertThat(
                `
                class Spaceship {}
                class Navigator {}
                class Route {}
                `,
            ).hasNoError(),
    );

    it(
        'Meaningless class names',
        () =>
            assertThat(
                `
                class SpaceshipManager {}
                class NavigationManager {}
                class RouteManager {}
                `,
            ).hasErrorMessages(
                "Avoid meaningless word 'Manager'.",
                "Avoid meaningless word 'Manager'.",
                "Avoid meaningless word 'Manager'.",
            ),
    );

    it(
        'Utility class found',
        () =>
            assertThat(
                `
                class SpaceshipUtil {}
                `,
            ).hasErrorMessages("Rename utility class to 'Spaceships'."),
    );

    it(
        'Utility file found',
        () =>
            assertThat('// comment')
                .withFilename('spaceship-util.ts')
                .hasErrorMessages("Rename utility class to 'Spaceships'."),
    );
});
