package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class RedundantIfRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { RedundantIfRule() }

    @Test
    fun `Rule properties`() = RedundantIfRule().assertProperties()

    @Test
    fun `If-else do not contain boolean constants`() =
        assertThatCode(
            """
            fun foo(): Boolean {
                if (true) {
                    return true
                } else {
                    return "Lorem" == "ipsum"
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `If-else contain boolean constants`() =
        assertThatCode(
            """
            fun foo(): Boolean {
                if (true) {
                    return true
                } else {
                    return false
                }
            }

            fun bar(): Boolean {
                if (true) {
                    return true
                }
                return false
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 5, "Omit redundant 'if' condition."),
            LintViolation(10, 5, "Omit redundant 'if' condition."),
        )

    @Test
    fun `Capture conditions without blocks`() =
        assertThatCode(
            """
            fun foo(): Boolean {
                if (true) return true
                else return false
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, "Omit redundant 'if' condition.")

    @Test
    fun `Capture trailing non-ifs`() =
        assertThatCode(
            """
            fun foo(): Boolean {
                if (true) {
                    return true
                } else {
                    return false
                }

                // Lorem ipsum.
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, "Omit redundant 'if' condition.")

    @Test
    fun `Capture property initialization`() =
        assertThatCode(
            """
            fun foo() {
                val bar =
                    if (true) {
                        true
                    } else {
                        false
                    }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(3, 9, "Omit redundant 'if' condition.")
}
