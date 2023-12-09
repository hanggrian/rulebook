package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.IfStatementNestingRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class IfStatementNestingRuleTest {
    private val assertThatCode = assertThatRule { IfStatementNestingRule() }

    @Test
    fun `Empty then statement`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Inverted if statement`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    return
                }
                bar()
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Only 1 line in if statement`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    bar()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `At least 2 lines in if statement`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    bar()
                    baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])

    @Test
    fun `If statement with else`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    bar()
                    baz()
                } else if (false) {
                    bar()
                    baz()
                } else {
                    bar()
                    baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
