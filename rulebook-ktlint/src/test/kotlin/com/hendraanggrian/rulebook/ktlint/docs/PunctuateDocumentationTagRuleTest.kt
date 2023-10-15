package com.hendraanggrian.rulebook.ktlint.docs

import com.hendraanggrian.rulebook.ktlint.Messages
import com.hendraanggrian.rulebook.ktlint.docs.PunctuateDocumentationTagRule.Companion.MSG
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class PunctuateDocumentationTagRuleTest {
    private val assertThatCode = assertThatRule { PunctuateDocumentationTagRule() }

    @Test
    fun `No description`() = assertThatCode(
        """
        /**
         * @param input
         * @throws NumberFormatException
         * @return
         */
        fun add(input: Int): Int = input
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Descriptions end with punctuations`() = assertThatCode(
        """
        /**
         * @param input a number.
         * @param input a number!
         * @param input a number?
         * @param input some
         *   long number.
         * @param input some
         *   long number?
         * @param input some
         *   long number!
         * @throws NumberFormatException some error.
         * @return a number.
         */
        fun add(input: Int): Int = input
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Descriptions have no end punctuation`() = assertThatCode(
        """
        /**
         * @param input a number
         * @param input some
         *   long number
         * @throws NumberFormatException some error
         * @return a number
         */
        fun add(input: Int): Int = input
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(2, 10, Messages[MSG]),
        LintViolation(3, 10, Messages[MSG]),
        LintViolation(5, 11, Messages[MSG]),
        LintViolation(6, 11, Messages[MSG])
    )

    @Test
    fun `Description end with comments`() = assertThatCode(
        """
        /**
         * @param input a number // some comment
         * @param input a. // some comment
         * @throws NumberFormatException some error // some comment
         * @return a number. // some comment
         */
        fun add(input: Int): Int = input
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(2, 10, Messages[MSG]),
        LintViolation(4, 11, Messages[MSG])
    )
}
