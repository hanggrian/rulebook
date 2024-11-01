package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.BlockCommentWrappingRule.Companion.MSG_JOIN
import com.hanggrian.rulebook.ktlint.BlockCommentWrappingRule.Companion.MSG_SPLIT
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class BlockCommentWrappingRuleTest {
    private val assertThatCode = assertThatRule { BlockCommentWrappingRule() }

    @Test
    fun `Rule properties`() = BlockCommentWrappingRule().assertProperties()

    @Test
    fun `Correct block comments`() =
        assertThatCode(
            """
            /** Short comment. */
            fun foo() {}

            /**
             * Very long
             * comment.
             */
            fun bar() {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Multi-line block comment that can be converted into single-line`() =
        assertThatCode(
            """
            /**
             * Short comment.
             */
            fun foo() {}
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 3, Messages[MSG_JOIN])

    @Test
    fun `Long block comment that should be converted into multi-line`() =
        assertThatCode(
            """
            /** Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. */
            fun foo() {}
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 4, Messages[MSG_SPLIT])

    @Test
    fun `Skip tagged block comment`() =
        assertThatCode(
            """
            /**
             * @param bar some value.
             */
            fun foo(bar: Int) {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip empty block comment`() =
        assertThatCode(
            """
            /**
             *
             */
            fun foo(bar: Int) {}
            """.trimIndent(),
        ).hasNoLintViolations()
}
