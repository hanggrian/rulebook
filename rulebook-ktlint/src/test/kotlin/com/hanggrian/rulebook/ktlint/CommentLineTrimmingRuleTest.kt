package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.CommentLineTrimmingRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class CommentLineTrimmingRuleTest {
    private val assertThatCode = assertThatRule { CommentLineTrimmingRule() }

    @Test
    fun `Rule properties`(): Unit = CommentLineTrimmingRule().assertProperties()

    @Test
    fun `Comment without initial and final newline`() =
        assertThatCode(
            """
            // Lorem ipsum.
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Comment with initial and final newline`() =
        assertThatCode(
            """
            //
            // Lorem ipsum.
            //
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, Messages[MSG]),
            LintViolation(3, 1, Messages[MSG]),
        )

    @Test
    fun `Skip blank comment`() =
        assertThatCode(
            """

            //

            """.trimIndent(),
        ).hasNoLintViolations()
}
