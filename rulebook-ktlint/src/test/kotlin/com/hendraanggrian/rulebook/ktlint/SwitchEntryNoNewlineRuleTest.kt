package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class SwitchEntryNoNewlineRuleTest {
    private val assertThatCode = assertThatRule { SwitchEntryNoNewlineRule() }

    @Test
    fun `Empty line center`() = assertThatCode(
        """
        fun main() {
            when {
                true -> {}

                else -> {}
            }
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(5, 9, Messages[SwitchEntryNoNewlineRule.MSG])
}
