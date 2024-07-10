package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.ConditionalBranchLineJoiningRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ConditionalBranchLineJoiningRuleTest {
    private val assertThatCode = assertThatRule { ConditionalBranchLineJoiningRule() }

    @Test
    fun `Rule properties`(): Unit = ConditionalBranchLineJoiningRule().assertProperties()

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
