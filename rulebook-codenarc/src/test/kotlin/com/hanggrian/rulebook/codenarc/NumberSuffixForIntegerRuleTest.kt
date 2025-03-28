package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.NumberSuffixForIntegerRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class NumberSuffixForIntegerRuleTest : AbstractRuleTestCase<NumberSuffixForIntegerRule>() {
    override fun createRule() = NumberSuffixForIntegerRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<NumberSuffixForIntegerRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Lowercase literal integers`() =
        assertNoViolations(
            """
            var foo = 0i

            def bar() {
                println(123i)
            }
            """.trimIndent(),
        )

    @Test
    fun `Uppercase literal integers`() =
        assertTwoViolations(
            """
            var foo = 0I

            def bar() {
                println(123I)
            }
            """.trimIndent(),
            1,
            "var foo = 0I",
            Messages[MSG],
            4,
            "println(123I)",
            Messages[MSG],
        )

    @Test
    fun `Skip hexadecimals`() =
        assertNoViolations(
            """
            var foo = 0x00f

            def bar() {
                println(0x123f)
            }
            """.trimIndent(),
        )
}
