package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.BlockCommentTrimRule.Companion.MSG_FIRST
import com.hanggrian.rulebook.ktlint.BlockCommentTrimRule.Companion.MSG_LAST
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockCommentTrimRuleTest {
    private val assertThatCode = assertThatRule { BlockCommentTrimRule() }

    @Test
    fun `Rule properties`() = BlockCommentTrimRule().assertProperties()

    @Test
    fun `Block comment without initial and final newline`() =
        assertThatCode(
            """
            /**
             * Lorem ipsum.
             */
            fun foo()
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Block comment with initial and final newline`() =
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

    @Test
    fun `Skip single-line block comment`() =
        assertThatCode(
            """
            /** Lorem ipsum. */
            fun foo(): Int
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip blank block comment`() =
        assertThatCode(
            """
            /**
             *
             */
            fun foo(): Int
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip multiline block tag description`() =
        assertThatCode(
            """
            /**
             * @param bar Lorem ipsum
             *   dolor sit amet.
             */
            fun foo(bar: Int): Int
            """.trimIndent(),
        ).hasNoLintViolations()
}
