package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.SourceWordMeaningVisitor.Companion.MSG_ALL
import com.hendraanggrian.rulebook.codenarc.SourceWordMeaningVisitor.Companion.MSG_UTIL
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class SourceWordMeaningRuleTest : AbstractRuleTestCase<SourceWordMeaningRule>() {
    override fun createRule() = SourceWordMeaningRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("SourceWordMeaning", rule.name)
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
