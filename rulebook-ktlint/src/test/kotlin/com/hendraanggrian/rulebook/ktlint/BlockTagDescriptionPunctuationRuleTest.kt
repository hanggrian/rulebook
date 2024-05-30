package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.BlockTagDescriptionPunctuationRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockTagDescriptionPunctuationRuleTest {
    private val assertThatCode = assertThatRule { BlockTagDescriptionPunctuationRule() }

    @Test
    fun `Rule properties`(): Unit = BlockTagDescriptionPunctuationRule().assertProperties()

    @Test
    fun `No description`() =
        assertThatCode(
            """
            /**
             * @param num
             * @return
             */
            fun add(num: Int): Int
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Descriptions end with a period`() =
        assertThatCode(
            """
            /**
             * @param num value.
             * @return total value.
             */
            fun add(num: Int): Int
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Descriptions don't end with a period`() =
        assertThatCode(
            """
            /**
             * @param num value
             * @return total value
             */
            fun add(num: Int): Int
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 20, Messages.get(MSG, "@param, @return")),
            LintViolation(3, 23, Messages.get(MSG, "@param, @return")),
        )

    @Test
    fun `Long descriptions`() =
        assertThatCode(
            """
            /**
             * @param num
             *   value
             * @return total
             *   value
             */
            fun add(num: Int): Int
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 11, Messages.get(MSG, "@param, @return")),
            LintViolation(5, 11, Messages.get(MSG, "@param, @return")),
        )
}
