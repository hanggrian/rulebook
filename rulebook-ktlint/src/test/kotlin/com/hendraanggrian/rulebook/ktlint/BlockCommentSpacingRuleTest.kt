package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.BlockCommentSpacingRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockCommentSpacingRuleTest {
    private val assertThatCode = assertThatRule { BlockCommentSpacingRule() }

    @Test
    fun `Correct format`() =
        assertThatCode(
            """
            /**
             * Just a box.
             *
             * @param width
             * @param height
             */
            fun createRectangle(width: Int, height: Int) { }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `No summary are fine`() =
        assertThatCode(
            """
            /**
             * @param width
             * @param height
             */
            fun createRectangle(width: Int, height: Int) { }

            /**
             *
             * @param width
             * @param height
             */
            fun createRectangle(width: Int, height: Int) { }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Missing empty line from summary`() =
        assertThatCode(
            """
            /**
             * Just a box.
             * @param width
             * @param height
             */
            fun summary(width: Int, height: Int) { }

            /**
             * [Box].
             * @param width
             * @param height
             */
            fun justLink(width: Int, height: Int) { }

            /**
             * `Box`.
             * @param width
             * @param height
             */
            fun justCode(width: Int, height: Int) { }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 4, Messages[MSG]),
            LintViolation(10, 4, Messages[MSG]),
            LintViolation(17, 4, Messages[MSG]),
        )
}
