package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.codenarc.VariableNameDisallowingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class VariableNameDisallowingRuleTest : AbstractRuleTestCase<VariableNameDisallowingRule>() {
    override fun createRule() = VariableNameDisallowingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<VariableNameDisallowingRule.Visitor>(rule.astVisitor)

        val rule = VariableNameDisallowingRule()
        rule.setNames("foo, bar")
        assertThat(rule.names).containsExactly("foo", "bar")
    }

    @Test
    fun `Descriptive field names`() =
        assertNoViolations(
            """
            class Foo {
                var name = ''
            }
            """.trimIndent(),
        )

    @Test
    fun `Prohibited field names`() =
        assertSingleViolation(
            """
            class Foo {
                var string = ''
            }
            """.trimIndent(),
            2,
            "var string = ''",
            Messages[MSG],
        )
}
