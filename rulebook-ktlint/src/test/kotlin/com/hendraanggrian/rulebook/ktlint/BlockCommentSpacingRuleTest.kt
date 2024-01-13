package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.BlockCommentSpacingRule.Companion.MSG_LINE_END
import com.hendraanggrian.rulebook.ktlint.BlockCommentSpacingRule.Companion.MSG_LINE_START
import com.hendraanggrian.rulebook.ktlint.BlockCommentSpacingRule.Companion.MSG_MULTILINE_START
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockCommentSpacingRuleTest {
    private val assertThatCode = assertThatRule { BlockCommentSpacingRule() }

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
            LintViolation(1, 4, Messages[MSG_LINE_START]),
            LintViolation(1, 12, Messages[MSG_LINE_END]),
            LintViolation(5, 3, Messages[MSG_MULTILINE_START]),
            LintViolation(7, 3, Messages[MSG_MULTILINE_START]),
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
            LintViolation(2, 3, Messages[MSG_MULTILINE_START]),
            LintViolation(4, 3, Messages[MSG_MULTILINE_START]),
            LintViolation(5, 3, Messages[MSG_MULTILINE_START]),
            LintViolation(6, 3, Messages[MSG_MULTILINE_START]),
        )

    @Test
    fun `Single line with block tag`() =
        assertThatCode(
            """
            /**@see java.util.List*/
            fun foo(num: Int) {}

            /** @see java.util.List */
            fun bar(num: Int) {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 4, Messages[MSG_LINE_START]),
            LintViolation(1, 23, Messages[MSG_LINE_END]),
        )
}
