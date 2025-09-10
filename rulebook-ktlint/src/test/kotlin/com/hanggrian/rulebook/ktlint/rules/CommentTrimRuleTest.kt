package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class CommentTrimRuleTest {
    private val assertThatCode = assertThatRule { CommentTrimRule() }

    @Test
    fun `Rule properties`() = CommentTrimRule().assertProperties()

    @Test
    fun `Comment without initial and final newline`() =
        assertThatCode(
            """
            fun foo() {
                // Lorem ipsum.
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Comment with initial and final newline`() =
        assertThatCode(
            """
            fun foo() {
                //
                // Lorem ipsum.
                //
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 5, "Remove blank line after '//'."),
            LintViolation(4, 5, "Remove blank line after '//'."),
        )

    @Test
    fun `Skip blank comment`() =
        assertThatCode(
            """
            fun foo() {

                //

            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip comment with code`() =
        assertThatCode(
            """
            fun foo() {
                println() //
                println() // Lorem ipsum.
                println() //
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
