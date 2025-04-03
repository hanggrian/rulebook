package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class IllegalVariableNameRuleTest : AbstractRuleTestCase<IllegalVariableNameRule>() {
    override fun createRule() = IllegalVariableNameRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<IllegalVariableNameRule.Visitor>(rule.astVisitor)

        val rule = IllegalVariableNameRule()
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
            "Use descriptive name.",
        )
}
