package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class LonelyIfRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { LonelyIfRule() }

    @Test
    fun `Rule properties`() = LonelyIfRule().assertProperties()

    @Test
    fun `If not in else block`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    println()
                } else if (false) {
                    println()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `If in else block`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    println()
                } else {
                    if (false) {
                        println()
                    }
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(5, 9, "Replace 'else' with 'else if' condition.")

    @Test
    fun `Capture trailing non-ifs`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    println()
                } else {
                    if (false) {
                        println()
                    }

                    // Lorem ipsum.
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(5, 9, "Replace 'else' with 'else if' condition.")
}
