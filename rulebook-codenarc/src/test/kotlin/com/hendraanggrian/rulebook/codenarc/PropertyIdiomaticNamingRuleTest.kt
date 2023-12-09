package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.PropertyIdiomaticNamingVisitor.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class PropertyIdiomaticNamingRuleTest : AbstractRuleTestCase<PropertyIdiomaticNamingRule>() {
    override fun createRule() = PropertyIdiomaticNamingRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("PropertyIdiomaticNaming", rule.name)
    }

    @Test
    fun `Descriptive names`() =
        assertNoViolations(
            """
            class Foo {
                String name = ""

                List<String> text = []
            }
            """.trimIndent(),
        )

    @Test
    fun `Class names`() =
        assertTwoViolations(
            """
            class MyClass {
                String string = ""

                List<String> list = []
            }
            """.trimIndent(),
            2,
            "String string = \"\"",
            Messages[MSG],
            4,
            "List<String> list = []",
            Messages[MSG],
        )
}
