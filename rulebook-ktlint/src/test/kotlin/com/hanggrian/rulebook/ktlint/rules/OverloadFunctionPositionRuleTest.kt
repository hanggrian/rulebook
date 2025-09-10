package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class OverloadFunctionPositionRuleTest {
    private val assertThatCode = assertThatRule { OverloadFunctionPositionRule() }

    @Test
    fun `Rule properties`() = OverloadFunctionPositionRule().assertProperties()

    @Test
    fun `Overload function next to each other`() =
        assertThatCode(
            """
            class Foo {
                fun bar(a: Int) {}

                fun bar(a: String) {}

                fun baz() {}
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Overload function not next to each other`() =
        assertThatCode(
            """
            class Foo {
                fun bar(a: Int) {}

                fun baz() {}

                fun bar(a: String) {}
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(6, 5, "Move 'bar' next to each other.")

    @Test
    fun `Overload function not next to each other in root`() =
        assertThatCode(
            """
            fun bar(a: Int) {}

            fun baz() {}

            fun bar(a: String) {}
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(5, 1, "Move 'bar' next to each other.")
}
