package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.NumberSuffixForFloatRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class NumberSuffixForFloatRuleTest : AbstractRuleTestCase<NumberSuffixForFloatRule>() {
    override fun createRule() = NumberSuffixForFloatRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<NumberSuffixForFloatRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Lowercase literal floats`() =
        assertNoViolations(
            """
            var foo = 0f

            def bar() {
                println(123f)
            }
            """.trimIndent(),
        )

    @Test
    fun `Uppercase literal floats`() =
        assertTwoViolations(
            """
            var foo = 0F

            def bar() {
                println(123F)
            }
            """.trimIndent(),
            1,
            "var foo = 0F",
            Messages[MSG],
            4,
            "println(123F)",
            Messages[MSG],
        )
}
