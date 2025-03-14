package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.BlockCommentSignatureRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class BlockCommentSignatureRuleTest {
    private val assertThatCode = assertThatRule { BlockCommentSignatureRule() }

    @Test
    fun `Rule properties`() = BlockCommentSignatureRule().assertProperties()

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
        ).hasLintViolationWithoutAutoCorrect(2, 3, Messages[MSG])

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
            fun foo() {}
            """.trimIndent(),
        ).hasNoLintViolations()
}
