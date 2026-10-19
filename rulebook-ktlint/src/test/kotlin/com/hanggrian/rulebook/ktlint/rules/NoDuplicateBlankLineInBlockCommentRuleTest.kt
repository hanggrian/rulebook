package com.hanggrian.rulebook.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class NoDuplicateBlankLineInBlockCommentRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { NoDuplicateBlankLineInBlockCommentRule() }

    @Test
    fun `Rule properties`() = NoDuplicateBlankLineInBlockCommentRule().assertProperties()

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
        ).hasLintViolationWithoutAutoCorrect(4, 3, "Remove consecutive blank line after '*'.")
}
