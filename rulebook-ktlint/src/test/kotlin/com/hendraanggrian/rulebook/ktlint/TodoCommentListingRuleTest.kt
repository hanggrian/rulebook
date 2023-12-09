package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.TodoCommentListingRule.Companion.MSG_LOWERCASE
import com.hendraanggrian.rulebook.ktlint.TodoCommentListingRule.Companion.MSG_UNKNOWN
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class TodoCommentListingRuleTest {
    private val assertThatCode = assertThatRule { TodoCommentListingRule() }

    @Test
    fun `Uppercase TODO comments`() =
        assertThatCode(
            """
            // TODO: add tests

            /**
             * FIXME: fix bug
             */
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Lowercase TODO comments`() =
        assertThatCode(
            """
            // todo: add tests

            /**
             * fixme: fix bug
             */
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, Messages[MSG_LOWERCASE]),
            LintViolation(4, 2, Messages[MSG_LOWERCASE]),
        )

    @Test
    fun `Unknown TODO comments`() =
        assertThatCode(
            """
            // to-do: add tests

            /**
             * fix: fix bug
             */
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, Messages[MSG_UNKNOWN]),
            LintViolation(4, 2, Messages[MSG_UNKNOWN]),
        )
}
