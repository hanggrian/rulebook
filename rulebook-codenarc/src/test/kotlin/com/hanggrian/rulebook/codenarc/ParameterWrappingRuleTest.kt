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
            void foo(String a, int b) {}

            void bar() {
                foo(new StringBuilder().toString(), 0);
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline parameters each with newline`() =
        assertNoViolations(
            """
            void foo(
                String a,
                int b
            ) {}

            void bar() {
                foo(
                    new StringBuilder()
                        .toString(),
                    0
                );
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline parameters each without newline`() =
        assertTwoViolations(
            """
            void foo(
                String a, int b
            ) {}

            void bar() {
                foo(
                    new StringBuilder()
                        .toString(), 0
                );
            }
            """.trimIndent(),
            2,
            "String a, int b",
            Messages[MSG_ARGUMENT],
            8,
            ".toString(), 0",
            Messages[MSG_ARGUMENT],
        )
}
