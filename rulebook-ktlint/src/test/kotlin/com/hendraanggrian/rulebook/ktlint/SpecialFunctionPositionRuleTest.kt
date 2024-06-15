package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.SpecialFunctionPositionRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class SpecialFunctionPositionRuleTest {
    private val assertThatCode = assertThatRule { SpecialFunctionPositionRule() }

    @Test
    fun `Rule properties`(): Unit = SpecialFunctionPositionRule().assertProperties()

    @Test
    fun `Overridden function at the bottom`() =
        assertThatCode(
            """
            class Foo {
                fun bar() {}

                override fun toString() = "baz"
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Overridden class before function`() =
        assertThatCode(
            """
            class Foo {
                override fun toString() = "baz"

                fun bar() {}
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages.get(MSG, "toString"))

    @Test
    fun `Grouping overridden functions`() =
        assertThatCode(
            """
            class Foo(a: Int) {
                override fun toString() = "baz"

                override fun hashCode() = 0

                override fun equals(other: Any?) = true
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
