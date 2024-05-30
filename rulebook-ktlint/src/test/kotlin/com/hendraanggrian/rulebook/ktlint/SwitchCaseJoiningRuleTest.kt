package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.SwitchCaseJoiningRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class SwitchCaseJoiningRuleTest {
    private val assertThatCode = assertThatRule { SwitchCaseJoiningRule() }

    @Test
    fun `Rule properties`(): Unit = SwitchCaseJoiningRule().assertProperties()

    @Test
    fun `Joined switch case branches`() =
        assertThatCode(
            """
            fun method() {
                when (foo) {
                    true -> bar()
                    false -> { baz() }
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Separated switch case branches`() =
        assertThatCode(
            """
            fun method() {
                when (foo) {
                    true -> bar()

                    false -> { baz() }
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(5, 9, Messages[MSG])
}
