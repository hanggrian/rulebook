package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class UnnecessarySwitchRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { UnnecessarySwitchRule() }

    @Test
    fun `Rule properties`() = UnnecessarySwitchRule().assertProperties()

    @Test
    fun `Multiple switch branches`() =
        assertThatCode(
            """
            fun foo() {
                when (bar) {
                    0 -> baz()
                    1 -> baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Single switch branch`() =
        assertThatCode(
            """
            fun foo() {
                when (bar) {
                    0 -> baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, "Replace 'when' with 'if' condition.")

    @Test
    fun `Skip single branch if it has fall through condition`() =
        assertThatCode(
            """
            fun foo() {
                when (bar) {
                    1, 2 -> baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
