package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class EmptyBracesClipRuleTest {
    private val assertThatCode = assertThatRule { EmptyBracesClipRule() }

    @Test
    fun `Rule properties`() = EmptyBracesClipRule().assertProperties()

    @Test
    fun `Wrapped braces`() =
        assertThatCode(
            """
            class Foo {}

            fun bar() {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Unwrapped braces`() =
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
    fun `Allow braces with comment`() =
        assertThatCode(
            """
            class Foo {
                // Lorem ipsum.
            }
            """.trimIndent(),
        ).hasNoLintViolations()

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
    fun `Braces in parameter default value declaration`() =
        assertThatCode(
            """
            fun foo(
                bar: (Int) -> Unit = {},
                baz: (Int) -> Unit = { },
            ) {}
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(3, 27, "Convert into '{}'.")
}
