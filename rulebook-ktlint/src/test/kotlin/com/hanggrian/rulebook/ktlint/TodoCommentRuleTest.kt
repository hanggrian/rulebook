package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class TodoCommentRuleTest {
    private val assertThatCode = assertThatRule { TodoCommentRule() }

    @Test
    fun `Rule properties`() = TodoCommentRule().assertProperties()

    @Test
    fun `Uppercase TODO comments`() =
        assertThatCode(
            """
            // TODO add tests
            // FIXME fix bug
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Lowercase TODO comments`() =
        assertThatCode(
            """
            // todo add tests
            // fixme fix bug
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, "Capitalize keyword 'todo'."),
            LintViolation(2, 1, "Capitalize keyword 'fixme'."),
        )

    @Test
    fun `Unknown TODO comments`() =
        assertThatCode(
            """
            // TODO: add tests
            // FIXME1 fix bug
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, "Omit separator ':'."),
            LintViolation(2, 1, "Omit separator '1'."),
        )

    @Test
    fun `TODOs in block comments`() =
        assertThatCode(
            """
            /** todo add tests */

            /**
             * FIXME: memory leak
             */
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 4, "Capitalize keyword 'todo'."),
            LintViolation(4, 2, "Omit separator ':'."),
        )

    @Test
    fun `TODO keyword mid-sentence`() =
        assertThatCode(
            """
            // Untested. Todo: add tests.
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, "Capitalize keyword 'Todo'."),
            LintViolation(1, 1, "Omit separator ':'."),
        )
}
