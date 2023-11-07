package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.EndSentenceWithPeriodRule.Companion.MSG_COMMENT
import com.hendraanggrian.rulebook.ktlint.EndSentenceWithPeriodRule.Companion.MSG_TAG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class EndSentenceWithPeriodRuleTest {
    private val assertThatCode = assertThatRule { EndSentenceWithPeriodRule() }

    @Test
    fun `No sentence`() =
        assertThatCode(
            """
            /**
             * @param a
             * @param b
             * @return
             */
            fun sum(a: Int, b: Int): Int {
                //
                return a + b
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Sentences end with a period`() =
        assertThatCode(
            """
            /**
             * @param a first value.
             * @param b second value.
             * @return combined values.
             */
            fun sum(a: Int, b: Int): Int {
                // Combine both values.
                return a + b
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Sentences don't end with a period`() =
        assertThatCode(
            """
            /**
             * @param a first value
             * @param b second value
             * @return combined values
             */
            fun sum(a: Int, b: Int): Int {
                // Combine both values
                return a + b
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 24, Messages[MSG_TAG]),
            LintViolation(3, 25, Messages[MSG_TAG]),
            LintViolation(4, 27, Messages[MSG_TAG]),
            LintViolation(7, 27, Messages[MSG_COMMENT]),
        )

    @Test
    fun `Long sentences`() =
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
            fun sum(a: Int, b: Int): Int {
                // Combine both
                // values
                return a + b
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 11, Messages[MSG_TAG]),
            LintViolation(11, 14, Messages[MSG_COMMENT]),
        )
}
