package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.BlockCommentSpacingRule.Companion.MSG_MULTI
import com.hanggrian.rulebook.ktlint.BlockCommentSpacingRule.Companion.MSG_SINGLE_END
import com.hanggrian.rulebook.ktlint.BlockCommentSpacingRule.Companion.MSG_SINGLE_START
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockCommentSpacingRuleTest {
    private val assertThatCode = assertThatRule { BlockCommentSpacingRule() }

    @Test
    fun `Rule properties`() = BlockCommentSpacingRule().assertProperties()

    @Test
    fun `Untrimmed block comment`() =
        assertThatCode(
            """
            /** Summary. */
            fun foo() {}

            /**
             * Summary.
             *
             * @param num description.
             */
            fun bar(num: Int) {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Trimmed block comment`() =
        assertThatCode(
            """
            /**Summary.*/
            fun foo() {}

            /**
             *Summary.
             *
             *@param num description.
             */
            fun bar(num: Int) {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 4, Messages[MSG_SINGLE_START]),
            LintViolation(1, 12, Messages[MSG_SINGLE_END]),
            LintViolation(5, 3, Messages[MSG_MULTI]),
            LintViolation(7, 3, Messages[MSG_MULTI]),
        )

    @Test
    fun `Unconventional block tags`() =
        assertThatCode(
            """
            /**
             *Summary.
             *
             *@param num description.
             *@goodtag
             *@awesometag with description.
             */
            fun foo(num: Int) {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 3, Messages[MSG_MULTI]),
            LintViolation(4, 3, Messages[MSG_MULTI]),
            LintViolation(5, 3, Messages[MSG_MULTI]),
            LintViolation(6, 3, Messages[MSG_MULTI]),
        )
}
