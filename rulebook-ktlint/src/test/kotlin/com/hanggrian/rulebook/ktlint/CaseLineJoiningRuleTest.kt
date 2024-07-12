package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.CaseLineJoiningRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class CaseLineJoiningRuleTest {
    private val assertThatCode = assertThatRule { CaseLineJoiningRule() }

    @Test
    fun `Rule properties`() = CaseLineJoiningRule().assertProperties()

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
