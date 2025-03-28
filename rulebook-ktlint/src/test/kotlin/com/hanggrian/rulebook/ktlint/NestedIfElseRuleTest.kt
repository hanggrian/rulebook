package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.NestedIfElseRule.Companion.MSG_INVERT
import com.hanggrian.rulebook.ktlint.NestedIfElseRule.Companion.MSG_LIFT
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class NestedIfElseRuleTest {
    private val assertThatCode = assertThatRule { NestedIfElseRule() }

    @Test
    fun `Rule properties`() = NestedIfElseRule().assertProperties()

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
    fun `Skip block with jump statement`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                    return
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
    fun `Skip recursive if-else because it is not safe to return in inner blocks`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    if (true) {
                        baz()
                        baz()
                    }
                }
                baz()
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip initializer because you can't return in init block`() =
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
                    while (true) {
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
    fun `Skip if-else with return statement`() =
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
        ).hasNoLintViolations()
}
