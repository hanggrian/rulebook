package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class UnnecessaryContinueRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { UnnecessaryContinueRule() }

    @Test
    fun `Rule properties`() = UnnecessaryContinueRule().assertProperties()

    @Test
    fun `Loops don't end with continue`() =
        assertThatCode(
            """
            fun foo(items: List<Int>) {
                for (item in items) {
                    println()
                }
            }

            fun bar(items: List<Int>) {
                while (true) {
                    println()
                }
            }

            fun baz(items: List<Int>) {
                do {
                    println()
                } while (true)
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Loops end with continue`() =
        assertThatCode(
            """
            fun foo(items: List<Int>) {
                for (item in items) {
                    println()
                    continue
                }
            }

            fun bar(items: List<Int>) {
                while (true) {
                    println()
                    continue
                }
            }

            fun baz(items: List<Int>) {
                do {
                    println()
                    continue
                } while (true)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(4, 9, "Last 'continue' is not needed."),
            LintViolation(11, 9, "Last 'continue' is not needed."),
            LintViolation(18, 9, "Last 'continue' is not needed."),
        )

    @Test
    fun `Capture loops without block`() =
        assertThatCode(
            """
            fun foo(items: List<Int>) {
                for (item in items) continue
            }

            fun bar(items: List<Int>) {
                while (true) continue
            }

            fun baz(items: List<Int>) {
                do continue while (true)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 25, "Last 'continue' is not needed."),
            LintViolation(6, 18, "Last 'continue' is not needed."),
            LintViolation(10, 8, "Last 'continue' is not needed."),
        )

    @Test
    fun `Capture trailing non-continue`() =
        assertThatCode(
            """
            fun foo(items: List<Int>) {
                for (item in items) {
                    println()
                    continue

                    // Lorem ipsum.
                }
            }

            fun bar(items: List<Int>) {
                while (true) {
                    println()
                    continue

                    // Lorem ipsum.
                }
            }

            fun baz(items: List<Int>) {
                do {
                    println()
                    continue

                    // Lorem ipsum.
                } while (true)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(4, 9, "Last 'continue' is not needed."),
            LintViolation(13, 9, "Last 'continue' is not needed."),
            LintViolation(22, 9, "Last 'continue' is not needed."),
        )
}
