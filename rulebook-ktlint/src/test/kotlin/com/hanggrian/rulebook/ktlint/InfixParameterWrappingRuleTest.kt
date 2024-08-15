package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.InfixParameterWrappingRule.Companion.MSG_MISSING
import com.hanggrian.rulebook.ktlint.InfixParameterWrappingRule.Companion.MSG_UNEXPECTED
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class InfixParameterWrappingRuleTest {
    private val assertThatCode = assertThatRule { InfixParameterWrappingRule() }

    @Test
    fun `Rule properties`() = InfixParameterWrappingRule().assertProperties()

    @Test
    fun `Infix parameter in single line statement`() =
        assertThatCode(
            """
            fun foo() {
                println(0 or 1)
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Wrapping infix functions`() =
        assertThatCode(
            """
            fun foo() {
                println(
                    0
                        or 1
                        or 2,
                )
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(4, 13, Messages[MSG_UNEXPECTED]),
            LintViolation(5, 13, Messages[MSG_UNEXPECTED]),
        )

    @Test
    fun `Wrapping infix parameters`() =
        assertThatCode(
            """
            fun foo() {
                println(
                    0 or
                        1 or
                        2,
                )
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Multiline parameter need to be wrapped`() =
        assertThatCode(
            """
            fun foo() {
                println(
                    0 or maxOf(
                        1,
                        2,
                    ) or minOf(
                        1,
                        2,
                    ),
                )
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 14, Messages[MSG_MISSING]),
            LintViolation(6, 14, Messages[MSG_MISSING]),
        )
}
