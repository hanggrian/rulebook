package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.TodoCommentFormattingRule.Companion.MSG_KEYWORD
import com.hendraanggrian.rulebook.ktlint.TodoCommentFormattingRule.Companion.MSG_SEPARATOR
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class TodoCommentFormattingRuleTest {
    private val assertThatCode = assertThatRule { TodoCommentFormattingRule() }

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
            LintViolation(1, 1, Messages.get(MSG_KEYWORD, "todo")),
            LintViolation(2, 1, Messages.get(MSG_KEYWORD, "fixme")),
        )

    @Test
    fun `Unknown TODO comments`() =
        assertThatCode(
            """
            // TODO: add tests
            // FIXME1 fix bug
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, Messages.get(MSG_SEPARATOR, ":")),
            LintViolation(2, 1, Messages.get(MSG_SEPARATOR, "1")),
        )

    @Test
    fun `TODOs in multiline comment`() =
        assertThatCode(
            """
            /**
             * todo add tests
             */

            /**
             * FIXME: memory leak
             */
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 2, Messages.get(MSG_KEYWORD, "todo")),
            LintViolation(6, 2, Messages.get(MSG_SEPARATOR, ":")),
        )

    @Test
    fun `TODO keyword mid-sentence`() =
        assertThatCode(
            """
            // Untested. Todo: add tests.
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, Messages.get(MSG_KEYWORD, "Todo")),
            LintViolation(1, 1, Messages.get(MSG_SEPARATOR, ":")),
        )
}
