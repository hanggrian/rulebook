package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.ElvisWrapRule.Companion.MSG_MISSING
import com.hanggrian.rulebook.ktlint.ElvisWrapRule.Companion.MSG_UNEXPECTED
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ElvisWrapRuleTest {
    private val assertThatCode = assertThatRule { ElvisWrapRule() }

    @Test
    fun `Rule properties`() = ElvisWrapRule().assertProperties()

    @Test
    fun `Elvis in single line statement`() =
        assertThatCode(
            """
            fun foo() {
                "".takeIf { it.isEmpty() } ?: return
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Trailing elvis in multiline statement`() =
        assertThatCode(
            """
            fun foo() {
                ""
                    .takeIf { it.isEmpty() } ?: return
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(3, 34, Messages[MSG_MISSING])

    @Test
    fun `Elvis in last line of multiline statement`() =
        assertThatCode(
            """
            fun foo() {
                ""
                    .takeIf { it.isEmpty() }
                    ?: return
            }

            fun bar() {
                "".takeIf {
                    it.isEmpty()
                } ?: return
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Elvis after newline in multiline statement with code block`() =
        assertThatCode(
            """
            fun foo() {
                ""
                    .takeIf {
                        it.isEmpty()
                    }
                    ?: return
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(6, 9, Messages[MSG_UNEXPECTED])

    @Test
    fun `Consider multiple elvis operators in a statement`() =
        assertThatCode(
            """
            fun foo() {
                ""
                    .takeIf { it.isEmpty() } ?: ""
                    .takeIf { it.isEmpty() } ?: return
            }

            fun bar() {
                ""
                    .takeIf {
                        it.isEmpty()
                    }
                    ?: ""
                        .takeIf {
                            it.isEmpty()
                        }
                    ?: return
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 34, Messages[MSG_MISSING]),
            LintViolation(4, 34, Messages[MSG_MISSING]),
            LintViolation(12, 9, Messages[MSG_UNEXPECTED]),
            LintViolation(16, 9, Messages[MSG_UNEXPECTED]),
        )
}
