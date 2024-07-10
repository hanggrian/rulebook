package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.BlockCommentLineJoiningRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class BlockCommentLineJoiningRuleTest {
    private val assertThatCode = assertThatRule { BlockCommentLineJoiningRule() }

    @Test
    fun `Rule properties`(): Unit = BlockCommentLineJoiningRule().assertProperties()

    @Test
    fun `Single empty line in block comment`() =
        assertThatCode(
            """
            /**
             * Lorem ipsum
             *
             * dolor sit amet.
             */
            fun foo() {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Multiple empty lines in block comment`() =
        assertThatCode(
            """
            /**
             * Lorem ipsum
             *
             *
             * dolor sit amet.
             */
            fun foo() {}
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 3, Messages[MSG])
}
