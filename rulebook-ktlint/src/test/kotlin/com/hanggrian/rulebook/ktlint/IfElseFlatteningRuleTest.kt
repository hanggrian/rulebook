package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.IfElseFlatteningRule.Companion.MSG_INVERT
import com.hanggrian.rulebook.ktlint.IfElseFlatteningRule.Companion.MSG_LIFT
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
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
    fun `Invert if with multiline statement or two statements`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                    baz()
                }
            }

            fun bar() {
                if (true) {
                    baz(
                        0,
                    )
                }
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 5, Messages[MSG_INVERT]),
            LintViolation(9, 5, Messages[MSG_INVERT]),
        )

    @Test
    fun `Lift else when there is no else if`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                } else {
                    baz()
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

    @Test
    fun `Skip init block`() =
        assertThatCode(
            """
            class Foo {
                init {
                    if (true) {
                        baz()
                        baz()
                    }
                }
            }

            class Bar {
                init {
                    if (true) {
                        baz()
                    } else {
                        if (true) {
                            baz()
                            baz()
                        }
                    }
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `If-else with return statement`() =
        assertThatCode(
            """
            fun foo(): Int {
                return if (true) {
                    baz()
                } else {
                    baz()
                    baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(4, 7, Messages[MSG_LIFT])
}
