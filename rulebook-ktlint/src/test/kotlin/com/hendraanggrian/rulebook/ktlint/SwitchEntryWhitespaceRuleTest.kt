package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class SwitchEntryWhitespaceRuleTest {
    private val assertThatCode = assertThatRule { SwitchEntryWhitespaceRule() }

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
    ).hasLintViolationWithoutAutoCorrect(5, 9, Messages[SwitchEntryWhitespaceRule.MSG])
}
