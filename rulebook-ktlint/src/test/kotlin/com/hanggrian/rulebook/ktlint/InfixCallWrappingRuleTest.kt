package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.InfixCallWrappingRule.Companion.MSG_MISSING
import com.hanggrian.rulebook.ktlint.InfixCallWrappingRule.Companion.MSG_UNEXPECTED
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class InfixCallWrappingRuleTest {
    private val assertThatCode = assertThatRule { InfixCallWrappingRule() }

    @Test
    fun `Rule properties`() = InfixCallWrappingRule().assertProperties()

    @Test
    fun `Operators in single-line statement`() =
        assertThatCode(
            """
            fun foo() {
                println(1 and 2 or 3)
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `NL-wrapped operators in multi-line statement`() =
        assertThatCode(
            """
            fun foo() {
                println(
                    1
                        and 2
                        or 3,
                )
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(4, 13, Messages.get(MSG_UNEXPECTED, "and")),
            LintViolation(5, 13, Messages.get(MSG_UNEXPECTED, "or")),
        )

    @Test
    fun `EOL-wrapped operators in multi-line statement`() =
        assertThatCode(
            """
            fun foo() {
                println(
                    1 and
                        2 or
                        3,
                )
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Multiline operand need to be wrapped`() =
        assertThatCode(
            """
            fun foo() {
                println(
                    1 and minOf(
                        2,
                        3,
                    ) or maxOf(
                        4,
                        5,
                    ),
                )
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 15, Messages.get(MSG_MISSING, "and")),
            LintViolation(6, 14, Messages.get(MSG_MISSING, "or")),
        )
}
