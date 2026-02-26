package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class UnnecessaryReturnRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { UnnecessaryReturnRule() }

    @Test
    fun `Rule properties`() = UnnecessaryReturnRule().assertProperties()

    @Test
    fun `Function doesn't end with return`() =
        assertThatCode(
            """
            fun foo() {
                println()
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Function end with return`() =
        assertThatCode(
            """
            fun foo() {
                println()
                return
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(3, 5, "Last 'return' is not needed.")

    @Test
    fun `Capture trailing non-return`() =
        assertThatCode(
            """
            fun foo() {
                println()
                return

                // Lorem ipsum.
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(3, 5, "Last 'return' is not needed.")

    @Test
    fun `Skip return statement with value`() =
        assertThatCode(
            """
            fun foo(): Boolean {
                println()
                return true
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
