package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.DefaultDenestingRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class DefaultDenestingRuleTest {
    private val assertThatCode = assertThatRule { DefaultDenestingRule() }

    @Test
    fun `Rule properties`() = DefaultDenestingRule().assertProperties()

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
        ).hasLintViolationWithoutAutoCorrect(5, 9, Messages[MSG])

    @Test
    fun `Skip if not all case blocks have return or throw`() =
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
}
