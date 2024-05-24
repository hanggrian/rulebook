package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.IdentifierNameBlacklistingRule.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class IdentifierNameBlacklistingRuleTest : AbstractRuleTestCase<IdentifierNameBlacklistingRule>() {
    override fun createRule() = IdentifierNameBlacklistingRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

    @Test
    fun `Descriptive field names`() =
        assertNoViolations(
            """
            class Foo {
                String name = ""
            }
            """.trimIndent(),
        )

    @Test
    fun `Prohibited field names`() =
        assertSingleViolation(
            """
            class Foo {
                String string = ""
            }
            """.trimIndent(),
            2,
            "String string = \"\"",
            Messages[MSG],
        )
}
