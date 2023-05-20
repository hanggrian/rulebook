package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.SwitchEntryWhitespaceRule.Companion.ERROR_MESSAGE
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
    ).hasLintViolationWithoutAutoCorrect(5, 9, ERROR_MESSAGE)
}
