package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class IllegalClassFinalNameRuleTest : AbstractRuleTestCase<IllegalClassFinalNameRule>() {
    override fun createRule() = IllegalClassFinalNameRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<IllegalClassFinalNameRule.Visitor>(rule.astVisitor)

        val rule = IllegalClassFinalNameRule()
        rule.setNames("Hello, World")
        assertThat(rule.names).containsExactly("Hello", "World")
    }

    @Test
    fun `Meaningful class names`() =
        assertNoViolations(
            """
            class Spaceship {}

            interface Rocket {}

            @interface Navigator {}

            enum Planet {}
            """.trimIndent(),
        )

    @Test
    fun `Meaningless class names`() =
        assertViolations(
            """
            class SpaceshipManager {}

            interface RocketManager {}

            @interface NavigatorManager {}

            enum PlanetManager {}
            """.trimIndent(),
            violationOf(1, "class SpaceshipManager {}", "Avoid meaningless word 'Manager'."),
            violationOf(3, "interface RocketManager {}", "Avoid meaningless word 'Manager'."),
            violationOf(5, "@interface NavigatorManager {}", "Avoid meaningless word 'Manager'."),
            violationOf(7, "enum PlanetManager {}", "Avoid meaningless word 'Manager'."),
        )

    @Test
    fun `Utility class found`() =
        assertSingleViolation(
            """
            class SpaceshipUtil {}
            """.trimIndent(),
            1,
            "class SpaceshipUtil {}",
            "Rename utility class to 'Spaceships'.",
        )
}
