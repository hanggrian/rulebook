package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.DuplicateBlankLineInCommentRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class DuplicateBlankLineInCommentRuleTest {
    private val assertThatCode = assertThatRule { DuplicateBlankLineInCommentRule() }

    @Test
    fun `Rule properties`() = DuplicateBlankLineInCommentRule().assertProperties()

    @Test
    fun `Single empty line in EOL comment`() =
        assertThatCode(
            """
            fun foo() {
                // Lorem ipsum
                //
                // dolor sit amet.
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Multiple empty lines in EOL comment`() =
        assertThatCode(
            """
            fun foo() {
                // Lorem ipsum
                //
                //
                // dolor sit amet.
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 7, Messages[MSG])
}
