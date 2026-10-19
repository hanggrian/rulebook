package com.hanggrian.rulebook.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class BlockCommentClippingRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { BlockCommentClippingRule() }

    @Test
    fun `Rule properties`() = BlockCommentClippingRule().assertProperties()

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
        ).hasLintViolationWithoutAutoCorrect(1, 1, "Convert into single-line.")

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
}
