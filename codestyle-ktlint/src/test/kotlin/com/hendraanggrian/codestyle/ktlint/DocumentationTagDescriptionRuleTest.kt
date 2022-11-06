package com.hendraanggrian.codestyle.ktlint

import com.hendraanggrian.codestyle.ktlint.DocumentationTagDescriptionRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class DocumentationTagDescriptionRuleTest {
    private val assertCode = assertThatRule { DocumentationTagDescriptionRule() }

    @Test
    fun `Tag description is not a sentence`() {
        assertCode(
            """
                /**
                 * @param input a number // violation
                 * @param input . // fine because suffix is period
                 */
                fun add(input: Int) { }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 4, ERROR_MESSAGE.format("@param"))
        )
    }

    @Test
    fun `Tag description is only link`() {
        assertCode(
            """
                /**
                 * @param input [Int] // violation
                 * @param input a [Int] // violation
                 * @param input [Int] a // violation
                 * @param input [Int]. // fine because suffix is period
                 */
                fun add(input: Int) { }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 4, ERROR_MESSAGE.format("@param")),
            LintViolation(3, 4, ERROR_MESSAGE.format("@param")),
            LintViolation(4, 4, ERROR_MESSAGE.format("@param"))
        )
    }
}
