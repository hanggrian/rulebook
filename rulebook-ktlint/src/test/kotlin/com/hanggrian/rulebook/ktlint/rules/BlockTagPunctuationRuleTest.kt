package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockTagPunctuationRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { BlockTagPunctuationRule() }

    @Test
    fun `Rule properties`() = BlockTagPunctuationRule().assertProperties()

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
    fun `Descriptions end without a period`() =
        assertThatCode(
            """
            /**
             * @param num value
             * @return total value
             */
            fun add(num: Int): Int
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 20, "End '@param' with a period."),
            LintViolation(3, 23, "End '@return' with a period."),
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
            LintViolation(3, 11, "End '@param' with a period."),
            LintViolation(5, 11, "End '@return' with a period."),
        )
}
