package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class DuplicateBlankLineInCommentRuleTest {
    private val assertThatCode = assertThatRule { DuplicateBlankLineInCommentRule() }

    @Test
    fun `Rule properties`() = DuplicateBlankLineInCommentRule().assertProperties()

    @Test
    fun `Single empty line in EOL comment`() =
        assertThatCode(
            """
            fun foo() {
                // Lorem ipsum
                //
                // dolor sit amet.
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Multiple empty lines in EOL comment`() =
        assertThatCode(
            """
            fun foo() {
                // Lorem ipsum
                //
                //
                // dolor sit amet.
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 7, "Remove consecutive blank line after '//'")
}
