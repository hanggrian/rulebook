package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.SwitchCaseBranchingRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class SwitchCaseBranchingRuleTest {
    private val assertThatCode = assertThatRule { SwitchCaseBranchingRule() }

    @Test
    fun `Rule properties`() = SwitchCaseBranchingRule().assertProperties()

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
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])
}