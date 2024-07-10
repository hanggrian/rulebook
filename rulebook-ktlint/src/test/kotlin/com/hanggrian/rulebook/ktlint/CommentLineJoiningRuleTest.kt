package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.CommentLineJoiningRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class CommentLineJoiningRuleTest {
    private val assertThatCode = assertThatRule { CommentLineJoiningRule() }

    @Test
    fun `Rule properties`(): Unit = CommentLineJoiningRule().assertProperties()

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
