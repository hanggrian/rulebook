package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.EmptyCommentLineJoiningRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class EmptyCommentLineJoiningRuleTest {
    private val assertThatCode = assertThatRule { EmptyCommentLineJoiningRule() }

    @Test
    fun `Rule properties`(): Unit = EmptyCommentLineJoiningRule().assertProperties()

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
