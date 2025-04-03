package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class InfixCallWrapRuleTest {
    private val assertThatCode = assertThatRule { InfixCallWrapRule() }

    @Test
    fun `Rule properties`() = InfixCallWrapRule().assertProperties()

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
            LintViolation(4, 13, "Omit newline before call 'and'."),
            LintViolation(5, 13, "Omit newline before call 'or'."),
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
            LintViolation(3, 15, "Put newline before call 'and'."),
            LintViolation(6, 14, "Put newline before call 'or'."),
        )
}
