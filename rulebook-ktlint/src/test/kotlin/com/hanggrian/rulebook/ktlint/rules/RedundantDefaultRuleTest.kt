package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class RedundantDefaultRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { RedundantDefaultRule() }

    @Test
    fun `Rule properties`() = RedundantDefaultRule().assertProperties()

    @Test
    fun `No throw or return in case`() =
        assertThatCode(
            """
            fun foo() {
                when (bar) {
                    0 -> baz()
                    1 -> { baz() }
                    else -> baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Lift else when case has return`() =
        assertThatCode(
            """
            fun foo() {
                when (bar) {
                    0 -> throw Exception()
                    1 -> { return }
                    else -> baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(5, 9, "Omit redundant 'else' condition.")

    @Test
    fun `Skip if not all case blocks have jump statement`() =
        assertThatCode(
            """
            fun foo() {
                when (bar) {
                    0 -> throw Exception()
                    1 -> { baz() }
                    else -> baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip jump statement with elvis`() =
        assertThatCode(
            """
            fun foo() {
                when (bar) {
                    0 -> baz() ?: throw Exception()
                    1 -> { baz() ?: return }
                    else -> baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip property assignment`() =
        assertThatCode(
            """
            fun foo() {
                val foo =
                    when (baz) {
                        0 -> return
                        else -> baz()
                    }
            }

            fun bar() {
                val (bar, bar2) =
                    when (baz) {
                        0 -> return
                        else -> baz()
                    }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
