package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test

class TodoCommentRuleTest : RuleTest<TodoCommentRule>() {
    override fun createRule() = TodoCommentRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Uppercase TODO comments`() =
        assertNoViolations(
            """
            // TODO add tests
            // FIXME fix bug
            """.trimIndent(),
        )

    @Test
    fun `Lowercase TODO comments`() =
        assertTwoViolations(
            """
            // todo add tests
            // fixme fix bug
            """.trimIndent(),
            1,
            "// todo add tests",
            "Capitalize keyword 'todo'.",
            2,
            "// fixme fix bug",
            "Capitalize keyword 'fixme'.",
        )

    @Test
    fun `Unknown TODO comments`() =
        assertTwoViolations(
            """
            // TODO: add tests
            // FIXME1 fix bug
            """.trimIndent(),
            1,
            "// TODO: add tests",
            "Omit separator ':'.",
            2,
            "// FIXME1 fix bug",
            "Omit separator '1'.",
        )

    @Test
    fun `TODOs in block comments`() =
        assertTwoViolations(
            """
            /** todo add tests */

            /**
             * FIXME: memory leak
             */
            """.trimIndent(),
            1,
            "/** todo add tests */",
            "Capitalize keyword 'todo'.",
            4,
            "* FIXME: memory leak",
            "Omit separator ':'.",
        )

    @Test
    fun `TODO keyword mid-sentence`() =
        assertTwoViolations(
            """
            // Untested. Todo: add tests.
            """.trimIndent(),
            1,
            "// Untested. Todo: add tests.",
            "Capitalize keyword 'Todo'.",
            1,
            "// Untested. Todo: add tests.",
            "Omit separator ':'.",
        )
}
