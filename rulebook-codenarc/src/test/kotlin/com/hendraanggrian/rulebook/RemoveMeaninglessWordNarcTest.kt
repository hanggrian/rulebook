package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.RemoveMeaninglessWordRule
import com.hendraanggrian.rulebook.codenarc.RemoveMeaninglessWordVisitor.Companion.MSG_PREFIX
import com.hendraanggrian.rulebook.codenarc.RemoveMeaninglessWordVisitor.Companion.MSG_SUFFIX
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoveMeaninglessWordNarcTest : AbstractRuleTestCase<RemoveMeaninglessWordRule>() {
    override fun createRule(): RemoveMeaninglessWordRule = RemoveMeaninglessWordRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("RemoveMeaninglessWord", rule.name)
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
                "message" to Messages.get(MSG_PREFIX, "Base"),
            ),
            mapOf(
                "line" to 2,
                "source" to "interface AbstractRocket {}",
                "message" to Messages.get(MSG_PREFIX, "Abstract"),
            ),
            mapOf(
                "line" to 3,
                "source" to "@interface NavigatorHelper {}",
                "message" to Messages.get(MSG_SUFFIX, "Helper"),
            ),
            mapOf(
                "line" to 4,
                "source" to "enum PlanetInfo {}",
                "message" to Messages.get(MSG_SUFFIX, "Info"),
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
                "message" to Messages.get(MSG_PREFIX, "Base"),
            ),
            mapOf(
                "line" to 1,
                "source" to "class BaseSpaceshipManager {}",
                "message" to Messages.get(MSG_SUFFIX, "Manager"),
            ),
        )
}
