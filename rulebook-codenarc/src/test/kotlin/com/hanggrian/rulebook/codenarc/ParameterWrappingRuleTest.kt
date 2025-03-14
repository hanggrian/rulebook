package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.ParameterWrappingRule.Companion.MSG_ARGUMENT
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class ParameterWrappingRuleTest : AbstractRuleTestCase<ParameterWrappingRule>() {
    override fun createRule() = ParameterWrappingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<ParameterWrappingRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Single-line parameters`() =
        assertNoViolations(
            """
            def foo(var a, var b) {}

            void bar() {
                foo(new StringBuilder().toString(), 0)
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline parameters each with newline`() =
        assertNoViolations(
            """
            def foo(
                var a,
                var b
            ) {}

            def bar() {
                foo(
                    new StringBuilder()
                        .toString(),
                    0
                )
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline parameters each without newline`() =
        assertTwoViolations(
            """
            def foo(
                var a, var b
            ) {}

            def bar() {
                foo(
                    new StringBuilder()
                        .toString(), 0
                )
            }
            """.trimIndent(),
            2,
            "var a, var b",
            Messages[MSG_ARGUMENT],
            8,
            ".toString(), 0",
            Messages[MSG_ARGUMENT],
        )
}
