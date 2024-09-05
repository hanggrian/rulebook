package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.codenarc.IdentifierNameDisallowingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class IdentifierNameDisallowingRuleTest : AbstractRuleTestCase<IdentifierNameDisallowingRule>() {
    override fun createRule() = IdentifierNameDisallowingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<IdentifierNameDisallowingRule.Visitor>(rule.astVisitor)

        val rule = IdentifierNameDisallowingRule()
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
