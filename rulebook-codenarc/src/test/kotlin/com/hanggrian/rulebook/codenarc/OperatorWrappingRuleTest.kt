package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.OperatorWrappingRule.Companion.MSG_MISSING
import com.hanggrian.rulebook.codenarc.OperatorWrappingRule.Companion.MSG_UNEXPECTED
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class OperatorWrappingRuleTest : AbstractRuleTestCase<OperatorWrappingRule>() {
    override fun createRule() = OperatorWrappingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<OperatorWrappingRule.Visitor>(rule.astVisitor)
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
            3,
            "1",
            Messages.get(MSG_UNEXPECTED, '+'),
            4,
            "+ 2",
            Messages.get(MSG_UNEXPECTED, '-'),
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
            Messages.get(MSG_MISSING, '+'),
            6,
            ") - Math.max(",
            Messages.get(MSG_MISSING, '-'),
        )
}
