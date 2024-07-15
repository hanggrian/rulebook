package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.IfElseFlatteningRule.Companion.MSG_INVERT
import com.hanggrian.rulebook.ktlint.IfElseFlatteningRule.Companion.MSG_LIFT
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class IfElseFlatteningRuleTest {
    private val assertThatCode = assertThatRule { IfElseFlatteningRule() }

    @Test
    fun `Rule properties`() = IfElseFlatteningRule().assertProperties()

    @Test
    fun `Empty or one statement in if statement`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                }
            }

            fun bar() {
                if (true) {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Invert if with two statements`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                    baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG_INVERT])

    @Test
    fun `Lift else when there is no else if`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 7, Messages[MSG_LIFT])

    @Test
    fun `Skip else if`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                    baz()
                } else if (false) {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Capture trailing non-ifs`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                    baz()
                }

                // Lorem ipsum.
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG_INVERT])
}
