package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.ClassFinalNameBlacklistingRule.Companion.MSG_ALL
import com.hanggrian.rulebook.codenarc.ClassFinalNameBlacklistingRule.Companion.MSG_UTIL
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class ClassFinalNameBlacklistingRuleTest : AbstractRuleTestCase<ClassFinalNameBlacklistingRule>() {
    override fun createRule() = ClassFinalNameBlacklistingRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

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
            violationOf(1, "class SpaceshipManager {}", Messages.get(MSG_ALL, "Manager")),
            violationOf(3, "interface RocketManager {}", Messages.get(MSG_ALL, "Manager")),
            violationOf(5, "@interface NavigatorManager {}", Messages.get(MSG_ALL, "Manager")),
            violationOf(7, "enum PlanetManager {}", Messages.get(MSG_ALL, "Manager")),
        )

    @Test
    fun `Utility class found`() =
        assertSingleViolation(
            """
            class SpaceshipUtil {}
            """.trimIndent(),
            1,
            "class SpaceshipUtil {}",
            Messages.get(MSG_UTIL, "Spaceships"),
        )
}
