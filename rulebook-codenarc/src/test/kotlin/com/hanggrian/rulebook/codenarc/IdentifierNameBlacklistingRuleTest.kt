package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.codenarc.IdentifierNameBlacklistingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class IdentifierNameBlacklistingRuleTest : AbstractRuleTestCase<IdentifierNameBlacklistingRule>() {
    override fun createRule() = IdentifierNameBlacklistingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<IdentifierNameBlacklistingRule.Visitor>(rule.astVisitor)

        val rule = IdentifierNameBlacklistingRule()
        rule.setNames("foo, bar")
        assertThat(rule.names).containsExactly("foo", "bar")
    }

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
