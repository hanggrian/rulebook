package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class MissingBlankLineBeforeBlockTagsRuleTest {
    private val assertThatCode = assertThatRule { MissingBlankLineBeforeBlockTagsRule() }

    @Test
    fun `Rule properties`() = MissingBlankLineBeforeBlockTagsRule().assertProperties()

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
             * @param num description.
             * @return description.
             */
            fun foo(num: Int): Int {}
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
            LintViolation(3, 4, "Add blank line before block tag group."),
            LintViolation(10, 4, "Add blank line before block tag group."),
            LintViolation(17, 4, "Add blank line before block tag group."),
        )
}
