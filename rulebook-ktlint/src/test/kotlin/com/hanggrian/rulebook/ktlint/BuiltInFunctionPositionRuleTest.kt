package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BuiltInFunctionPositionRuleTest {
    private val assertThatCode = assertThatRule { BuiltInFunctionPositionRule() }

    @Test
    fun `Rule properties`() = BuiltInFunctionPositionRule().assertProperties()

    @Test
    fun `Special function at the bottom`() =
        assertThatCode(
            """
            class Foo {
                fun bar() {}

                fun baz() {}

                override fun toString() = "foo"

                override fun hashCode() = 0
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Special function not at the bottom`() =
        assertThatCode(
            """
            class Foo {
                override fun toString() = "foo"

                fun bar() {}

                override fun hashCode() = 0

                fun baz() {}
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 5, "Move 'toString' to last."),
            LintViolation(6, 5, "Move 'hashCode' to last."),
        )

    @Test
    fun `Grouped overridden functions`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                override fun toString() = "foo"

                override fun hashCode() = 0

                override fun equals(other: Any?) = true
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
