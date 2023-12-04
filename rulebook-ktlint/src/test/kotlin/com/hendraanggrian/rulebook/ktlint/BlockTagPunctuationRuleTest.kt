package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.BlockTagPunctuationRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockTagPunctuationRuleTest {
    private val assertThatCode = assertThatRule { BlockTagPunctuationRule() }

    @Test
    fun `No description`() =
        assertThatCode(
            """
            /**
             * @param a
             * @param b
             * @return
             */
            fun sum(a: Int, b: Int): Int = a + b
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Descriptions end with a period`() =
        assertThatCode(
            """
            /**
             * @param a first value.
             * @param b second value.
             * @return combined values.
             */
            fun sum(a: Int, b: Int): Int = a + b
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Descriptions don't end with a period`() =
        assertThatCode(
            """
            /**
             * @param a first value
             * @param b second value
             * @return combined values
             */
            fun sum(a: Int, b: Int): Int = a + b
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 24, Messages.get(MSG, "@param, @return")),
            LintViolation(3, 25, Messages.get(MSG, "@param, @return")),
            LintViolation(4, 27, Messages.get(MSG, "@param, @return")),
        )

    @Test
    fun `Long descriptions`() =
        assertThatCode(
            """
            /**
             * @param a first
             *   value
             * @param b second
             *   value.
             * @return combined
             *   values.
             */
            fun sum(a: Int, b: Int): Int = a + b
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(3, 11, Messages.get(MSG, "@param, @return"))
}
