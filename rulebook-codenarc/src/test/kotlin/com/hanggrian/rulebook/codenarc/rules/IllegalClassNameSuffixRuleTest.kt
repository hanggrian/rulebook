package com.hanggrian.rulebook.codenarc.rules

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.codenarc.assertProperties
import com.hanggrian.rulebook.codenarc.violationOf
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class IllegalClassNameSuffixRuleTest : AbstractRuleTestCase<IllegalClassNameSuffixRule>() {
    override fun createRule() = IllegalClassNameSuffixRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<IllegalClassNameSuffixRule.Visitor>(rule.astVisitor)

        val rule = IllegalClassNameSuffixRule()
        rule.names = "Hello, World"
        assertThat(rule.nameList).containsExactly("Hello", "World")
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
