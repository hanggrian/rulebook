import { type AssertThat, assertThatRule } from 'testing-js/src/asserters';
import { describe, it } from 'vitest';
import meaninglessWordRule from '../../rules/meaningless-word';
import assertProperties from '../asserts';

describe('MeaninglessWordRuleTest', () => {
    const assertThat: AssertThat = assertThatRule(meaninglessWordRule);

    it('Rule properties', () => assertProperties(meaninglessWordRule));

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
                "2:23 Avoid meaningless word 'Manager'.",
                "3:23 Avoid meaningless word 'Manager'.",
                "4:23 Avoid meaningless word 'Manager'.",
            ),
    );

    it(
        'Utility class found',
        () =>
            assertThat(
                `
                class SpaceshipUtil {}
                `,
            ).hasErrorMessages("2:23 Rename utility class to 'Spaceships'."),
    );

    it(
        'Utility file found',
        () =>
            assertThat('// comment')
                .withFilename('spaceship-util.js')
                .hasErrorMessages("1:1 Rename utility class to 'Spaceships'."),
    );
});
