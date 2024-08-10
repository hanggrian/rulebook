package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.EmptyCodeBlockConcisenessRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class EmptyCodeBlockConcisenessRuleTest {
    private val assertThatCode = assertThatRule { EmptyCodeBlockConcisenessRule() }

    @Test
    fun `Rule properties`() = EmptyCodeBlockConcisenessRule().assertProperties()

    @Test
    fun `Wrapped empty block`() =
        assertThatCode(
            """
            fun foo() {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Unwrapped empty block`() =
        assertThatCode(
            """
            fun foo() { }

            fun bar() {
            }

            fun baz() {

            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 12, Messages[MSG]),
            LintViolation(3, 12, Messages[MSG]),
            LintViolation(6, 12, Messages[MSG]),
        )

    @Test
    fun `Code block in parameter default value declaration`() =
        assertThatCode(
            """
            class Foo(
                val bar: (Int) -> Unit = {},
                val baz: (Int) -> Unit = { },
            )
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 31, Messages[MSG]),
        )
}
