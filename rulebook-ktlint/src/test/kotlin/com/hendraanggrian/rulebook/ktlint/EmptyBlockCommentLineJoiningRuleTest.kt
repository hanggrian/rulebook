package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.EmptyBlockCommentLineJoiningRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class EmptyBlockCommentLineJoiningRuleTest {
    private val assertThatCode = assertThatRule { EmptyBlockCommentLineJoiningRule() }

    @Test
    fun `Rule properties`(): Unit = EmptyBlockCommentLineJoiningRule().assertProperties()

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
