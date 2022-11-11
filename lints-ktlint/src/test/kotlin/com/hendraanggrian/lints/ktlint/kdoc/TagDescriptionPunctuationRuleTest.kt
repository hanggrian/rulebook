package com.hendraanggrian.lints.ktlint.kdoc

import com.hendraanggrian.lints.ktlint.kdoc.TagDescriptionPunctuationRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class TagDescriptionPunctuationRuleTest {
    private val assertCode = assertThatRule { TagDescriptionPunctuationRule() }

    @Test
    fun `No description`() {
        assertCode(
            """
                /**
                 * @param input
                 */
                fun add(input: Int) { }
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Tag description ends with punctuation`() {
        assertCode(
            """
                /**
                 * @param input a number.
                 * @param input a number!
                 * @param input a number?
                 * @param input a
                 *   long number.
                 * @param input some
                 *   long number?
                 * @param input some
                 *   long number!
                 */
                fun add(input: Int) { }
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Tag description has no end punctuation`() {
        assertCode(
            """
                /**
                 * @param input a number
                 * @param input some
                 *   long number
                 */
                fun add(input: Int) { }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 4, ERROR_MESSAGE.format("@param")),
            LintViolation(3, 4, ERROR_MESSAGE.format("@param"))
        )
    }

    @Test
    fun `Tag description ends with comments`() {
        assertCode(
            """
                /**
                 * @param input a number // some comment
                 * @param input a. // some comment
                 * @param input // some comment
                 */
                fun add(input: Int) { }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 4, ERROR_MESSAGE.format("@param")),
            LintViolation(4, 4, ERROR_MESSAGE.format("@param"))
        )
    }
}
