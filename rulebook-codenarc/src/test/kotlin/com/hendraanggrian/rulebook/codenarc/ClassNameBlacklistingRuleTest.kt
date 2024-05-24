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
            mapOf(
                "line" to 1,
                "source" to "class SpaceshipManager {}",
                "message" to Messages.get(MSG_ALL, "Manager"),
            ),
            mapOf(
                "line" to 2,
                "source" to "interface RocketManager {}",
                "message" to Messages.get(MSG_ALL, "Manager"),
            ),
            mapOf(
                "line" to 3,
                "source" to "@interface NavigatorManager {}",
                "message" to Messages.get(MSG_ALL, "Manager"),
            ),
            mapOf(
                "line" to 4,
                "source" to "enum PlanetManager {}",
                "message" to Messages.get(MSG_ALL, "Manager"),
            ),
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
