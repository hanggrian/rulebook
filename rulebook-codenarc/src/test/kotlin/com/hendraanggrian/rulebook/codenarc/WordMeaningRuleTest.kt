package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.WordMeaningVisitor.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class WordMeaningRuleTest : AbstractRuleTestCase<WordMeaningRule>() {
    override fun createRule() = WordMeaningRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("WordMeaning", rule.name)
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
            class BaseSpaceship {}
            interface AbstractRocket {}
            @interface NavigatorHelper {}
            enum PlanetInfo {}
            """.trimIndent(),
            mapOf(
                "line" to 1,
                "source" to "class BaseSpaceship {}",
                "message" to Messages.get(MSG, "Base"),
            ),
            mapOf(
                "line" to 2,
                "source" to "interface AbstractRocket {}",
                "message" to Messages.get(MSG, "Abstract"),
            ),
            mapOf(
                "line" to 3,
                "source" to "@interface NavigatorHelper {}",
                "message" to Messages.get(MSG, "Helper"),
            ),
            mapOf(
                "line" to 4,
                "source" to "enum PlanetInfo {}",
                "message" to Messages.get(MSG, "Info"),
            ),
        )

    @Test
    fun `Violating both ends`() =
        assertViolations(
            """
            class BaseSpaceshipManager {}
            """.trimIndent(),
            mapOf(
                "line" to 1,
                "source" to "class BaseSpaceshipManager {}",
                "message" to Messages.get(MSG, "Base"),
            ),
            mapOf(
                "line" to 1,
                "source" to "class BaseSpaceshipManager {}",
                "message" to Messages.get(MSG, "Manager"),
            ),
        )
}
