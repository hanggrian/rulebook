package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.BlockCommentLineTrimmingRule.Companion.MSG_FIRST
import com.hendraanggrian.rulebook.ktlint.BlockCommentLineTrimmingRule.Companion.MSG_LAST
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockCommentLineTrimmingRuleTest {
    private val assertThatCode = assertThatRule { BlockCommentLineTrimmingRule() }

    @Test
    fun `Rule properties`(): Unit = BlockCommentLineTrimmingRule().assertProperties()

    @Test
    fun `Summary without initial and final newline`() =
        assertThatCode(
            """
            /**
             * Lorem ipsum.
             */
            fun foo()
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Summary with initial and final newline`() =
        assertThatCode(
            """
            /**
             *
             * Lorem ipsum.
             *
             */
            fun foo()
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 2, Messages[MSG_FIRST]),
            LintViolation(4, 2, Messages[MSG_LAST]),
        )

    @Test
    fun `Block tag description with final newline`() =
        assertThatCode(
            """
            /**
             * @return a number.
             *
             */
            fun foo(): Int
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(3, 2, Messages[MSG_LAST])
}
