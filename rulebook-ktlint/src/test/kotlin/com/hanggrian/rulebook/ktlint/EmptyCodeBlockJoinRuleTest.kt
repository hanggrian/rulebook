package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class EmptyCodeBlockJoinRuleTest {
    private val assertThatCode = assertThatRule { EmptyCodeBlockJoinRule() }

    @Test
    fun `Rule properties`() = EmptyCodeBlockJoinRule().assertProperties()

    @Test
    fun `Wrapped empty block`() =
        assertThatCode(
            """
            class Foo {}

            fun bar() {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Unwrapped empty block`() =
        assertThatCode(
            """
            class Foo { }

            fun bar() {
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 12, "Convert into '{}'."),
            LintViolation(3, 12, "Convert into '{}'."),
        )

    @Test
    fun `Control flows with multi-blocks`() =
        assertThatCode(
            """
            fun foo() {
                try {
                } catch (e: Exception) {
                }

                if (true) {
                } else if (false) {
                } else {
                }

                do {
                } while (true)
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Allow code block with comment`() =
        assertThatCode(
            """
            class Foo {
                // Lorem ipsum.
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Code block in parameter default value declaration`() =
        assertThatCode(
            """
            class Foo(
                val bar: (Int) -> Unit = {},
                val baz: (Int) -> Unit = { },
            )
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(3, 31, "Convert into '{}'.")
}
