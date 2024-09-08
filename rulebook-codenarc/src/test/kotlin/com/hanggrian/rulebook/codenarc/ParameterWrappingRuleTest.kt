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
            void foo(int a, int b) {}

            void bar() {
                foo(0, 1);
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline parameters each with newline`() =
        assertNoViolations(
            """
            void foo(
                int a,
                int b
            ) {}

            void bar() {
                foo(
                    0,
                    1
                );
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline parameters each without newline`() =
        assertTwoViolations(
            """
            void foo(
                int a, int b
            ) {}

            void bar() {
                foo(
                    0, 1
                );
            }
            """.trimIndent(),
            2,
            "int a, int b",
            Messages[MSG_ARGUMENT],
            7,
            "0, 1",
            Messages[MSG_ARGUMENT],
        )
}
