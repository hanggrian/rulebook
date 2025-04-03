package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class OperatorWrapRuleTest : AbstractRuleTestCase<OperatorWrapRule>() {
    override fun createRule() = OperatorWrapRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<OperatorWrapRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Operators in single-line statement`() =
        assertNoViolations(
            """
            def foo() {
                println(1 + 2 - 3)
            }
            """.trimIndent(),
        )

    @Test
    fun `NL-wrapped operators in multi-line statement`() =
        assertTwoViolations(
            """
            def foo() {
                println(
                    1
                        + 2
                        - 3
                )
            }
            """.trimIndent(),
            4,
            "+ 2",
            "Omit newline before operator '+'.",
            5,
            "- 3",
            "Omit newline before operator '-'.",
        )

    @Test
    fun `EOL-wrapped operators in multi-line statement`() =
        assertNoViolations(
            """
            def foo() {
                println(
                    1 +
                        2 -
                        3
                )
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline operand need to be wrapped`() =
        assertTwoViolations(
            """
            def foo() {
                println(
                    1 + Math.min(
                        2,
                        3
                    ) - Math.max(
                        4,
                        5
                    )
                )
            }
            """.trimIndent(),
            3,
            "1 + Math.min(",
            "Put newline before operator '+'.",
            6,
            ") - Math.max(",
            "Put newline before operator '-'.",
        )

    @Test
    fun `Skip assign operator`() =
        assertNoViolations(
            """
            def foo() {
                var bar =
                    1 +
                        2
            }
            """.trimIndent(),
        )
}
