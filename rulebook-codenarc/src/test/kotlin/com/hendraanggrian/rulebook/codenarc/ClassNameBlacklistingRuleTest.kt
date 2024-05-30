package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.ClassNameBlacklistingRule.Companion.MSG_ALL
import com.hendraanggrian.rulebook.codenarc.ClassNameBlacklistingRule.Companion.MSG_UTIL
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class ClassNameBlacklistingRuleTest : AbstractRuleTestCase<ClassNameBlacklistingRule>() {
    override fun createRule() = ClassNameBlacklistingRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

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
            violationOf(2, "interface RocketManager {}", Messages.get(MSG_ALL, "Manager")),
            violationOf(3, "@interface NavigatorManager {}", Messages.get(MSG_ALL, "Manager")),
            violationOf(4, "enum PlanetManager {}", Messages.get(MSG_ALL, "Manager")),
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
