package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.ElvisWrappingRule.Companion.MSG_MISSING
import com.hanggrian.rulebook.ktlint.ElvisWrappingRule.Companion.MSG_UNEXPECTED
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ElvisWrappingRuleTest {
    private val assertThatCode = assertThatRule { ElvisWrappingRule() }

    @Test
    fun `Rule properties`() = ElvisWrappingRule().assertProperties()

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
}
