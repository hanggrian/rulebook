package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class CaseSeparatorRuleTest {
    private val assertThatCode = assertThatRule { CaseSeparatorRule() }

    @Test
    fun `Rule properties`() = CaseSeparatorRule().assertProperties()

    @Test
    fun `No line break after single-line branch and line break after multiline branch`() =
        assertThatCode(
            """
            fun foo(bar: Int) {
                when (bar) {
                    0 -> baz()
                    1 -> {
                        baz()
                        baz()
                    }

                    else -> baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Line break after single-line branch`() =
        assertThatCode(
            """
            fun foo(bar: Int) {
                when (bar) {
                    0 -> baz()

                    else -> baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(3, 19, "Remove blank line after single-line branch.")

    @Test
    fun `No line break after multiline branch`() =
        assertThatCode(
            """
            fun foo(bar: Int) {
                when (bar) {
                    0 -> {
                        baz()
                        baz()
                    }
                    else -> {
                        baz()
                        baz()
                    }
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(6, 10, "Add blank line after multiline branch.")

    @Test
    fun `Branches with comment are always multiline`() =
        assertThatCode(
            """
            fun foo(bar: Int) {
                when (bar) {
                    // Lorem ipsum.
                    0 -> baz()
                    /* Lorem ipsum. */
                    1 -> baz()
                    /** Lorem ipsum. */
                    2 -> baz()
                    else -> baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(4, 19, "Add blank line after multiline branch."),
            LintViolation(6, 19, "Add blank line after multiline branch."),
            LintViolation(8, 19, "Add blank line after multiline branch."),
        )
}
