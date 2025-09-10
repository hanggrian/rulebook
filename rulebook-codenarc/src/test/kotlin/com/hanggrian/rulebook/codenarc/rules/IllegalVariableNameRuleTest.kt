package com.hanggrian.rulebook.codenarc.rules

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.codenarc.assertProperties
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
        rule.names = "foo, bar"
        assertThat(rule.nameList).containsExactly("foo", "bar")
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
