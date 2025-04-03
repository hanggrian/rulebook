package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class NumberSuffixForDoubleRuleTest : AbstractRuleTestCase<NumberSuffixForDoubleRule>() {
    override fun createRule() = NumberSuffixForDoubleRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<NumberSuffixForDoubleRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Lowercase literal doubles`() =
        assertNoViolations(
            """
            var foo = 0d

            def bar() {
                println(123d)
            }
            """.trimIndent(),
        )

    @Test
    fun `Uppercase literal doubles`() =
        assertTwoViolations(
            """
            var foo = 0D

            def bar() {
                println(123D)
            }
            """.trimIndent(),
            1,
            "var foo = 0D",
            "Tag double literal by 'd'.",
            4,
            "println(123D)",
            "Tag double literal by 'd'.",
        )
}
