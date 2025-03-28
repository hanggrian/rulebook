package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.BlockTagSeparatorRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockTagSeparatorRuleTest {
    private val assertThatCode = assertThatRule { BlockTagSeparatorRule() }

    @Test
    fun `Rule properties`() = BlockTagSeparatorRule().assertProperties()

    @Test
    fun `Separate summary and block tag group`() =
        assertThatCode(
            """
            /**
             * Summary.
             *
             * @param num description.
             * @return description.
             */
            fun foo(num: Int): Int {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `No summary are fine`() =
        assertThatCode(
            """
            /**
             *
             * @param num description.
             * @return description.
             */
            fun foo(num: Int): Int {}

            /**
             * @param num description.
             * @return description.
             */
            fun bar(num: Int): Int {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Missing empty line from summary`() =
        assertThatCode(
            """
            /**
             * Summary.
             * @param num description.
             * @return description.
             */
            fun foo(num: Int): Int {}

            /**
             * [Summary].
             * @param num description.
             * @return description.
             */
            fun bar(num: Int): Int {}

            /**
             * `Summary`.
             * @param num description.
             * @return description.
             */
            fun baz(num: Int): Int {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 4, Messages[MSG]),
            LintViolation(10, 4, Messages[MSG]),
            LintViolation(17, 4, Messages[MSG]),
        )
}
