package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class InnerClassPositionRuleTest {
    private val assertThatCode = assertThatRule { InnerClassPositionRule() }

    @Test
    fun `Rule properties`() = InnerClassPositionRule().assertProperties()

    @Test
    fun `Inner classes at the bottom`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                val bar = 0

                constructor() : this(0)

                fun baz() = print(0)

                class Inner

                class AnotherInner
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Inner classes before members`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                interface Inner

                val bar = 0

                class AnotherInner

                fun baz() = print(0)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 5, "Move inner class to the bottom."),
            LintViolation(6, 5, "Move inner class to the bottom."),
        )
}
