package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockCommentSpacesRuleTest {
    private val assertThatCode = assertThatRule { BlockCommentSpacesRule() }

    @Test
    fun `Rule properties`() = BlockCommentSpacesRule().assertProperties()

    @Test
    fun `Untrimmed block comment`() =
        assertThatCode(
            """
            /** Summary. */
            fun foo() {}

            /**
             * Summary.
             *
             * @param num description.
             */
            fun bar(num: Int) {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Trimmed block comment`() =
        assertThatCode(
            """
            /**Summary.*/
            fun foo() {}

            /**
             *Summary.
             *
             *@param num description.
             */
            fun bar(num: Int) {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 4, "Add space after '/**'."),
            LintViolation(1, 12, "Add space before '*/'."),
            LintViolation(5, 3, "Add space after '*'."),
            LintViolation(7, 3, "Add space after '*'."),
        )

    @Test
    fun `Unconventional block tags`() =
        assertThatCode(
            """
            /**
             *Summary.
             *
             *@param num description.
             *@goodtag
             *@awesometag with description.
             */
            fun foo(num: Int) {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 3, "Add space after '*'."),
            LintViolation(4, 3, "Add space after '*'."),
            LintViolation(5, 3, "Add space after '*'."),
            LintViolation(6, 3, "Add space after '*'."),
        )
}
