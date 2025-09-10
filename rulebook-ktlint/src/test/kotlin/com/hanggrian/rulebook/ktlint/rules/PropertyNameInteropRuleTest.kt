package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class PropertyNameInteropRuleTest {
    private val assertThatCode = assertThatRule { PropertyNameInteropRule() }

    @Test
    fun `Rule properties`() = PropertyNameInteropRule().assertProperties()

    @Test
    fun `Properties with is prefix`() =
        assertThatCode(
            """
            class Foo(val isBar: Boolean) {
                val isBaz: Boolean
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Properties without is prefix`() =
        assertThatCode(
            """
            class Foo(val bar: Boolean) {
                val baz: Boolean
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 15, "Rename property to 'isBar'."),
            LintViolation(2, 9, "Rename property to 'isBaz'."),
        )

    @Test
    fun `Skip annotated properties`() =
        assertThatCode(
            """
            class Foo(@JvmField val bar: Boolean) {
                val baz: Boolean
                    @JvmName("something")
                    get() = true
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip variables`() =
        assertThatCode(
            """
            fun foo() {
                val bar: Boolean = true
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Do not collect parameters or non-public fields`() =
        assertThatCode(
            """
            class Foo(bar: Boolean) {
                private var baz: Boolean = true
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
