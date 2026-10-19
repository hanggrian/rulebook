package com.hanggrian.rulebook.ktlint.rules

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class NoDuplicateBlankLineInCommentRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { NoDuplicateBlankLineInCommentRule() }

    @Test
    fun `Rule properties`() = NoDuplicateBlankLineInCommentRule().assertProperties()

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
