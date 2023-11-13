package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.SpecifyAccessExplicitlyRule.Companion.MSG_CLASS
import com.hendraanggrian.rulebook.ktlint.SpecifyAccessExplicitlyRule.Companion.MSG_FUNCTION
import com.hendraanggrian.rulebook.ktlint.SpecifyAccessExplicitlyRule.Companion.MSG_PROPERTY
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.VARIANT_PROPERTY
import com.hendraanggrian.rulebook.ktlint.internals.VariantValue
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class SpecifyAccessExplicitlyRuleTest {
    private val assertThatCode =
        assertThatRule(
            { SpecifyAccessExplicitlyRule() },
            editorConfigProperties = setOf(VARIANT_PROPERTY to VariantValue.library),
        )

    @Test
    fun `Property & function with access`() =
        assertThatCode(
            """
            public var length: Int = 0
            private fun size(): Int = length
            internal var lastIndex: Int
                get() = length - 1
            protected class MyList
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Property & function without access`() =
        assertThatCode(
            """
            var length: Int = 0
            fun size(): Int = length
            var lastIndex: Int
                get() = length - 1
            class MyList
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, Messages[MSG_PROPERTY]),
            LintViolation(2, 1, Messages[MSG_FUNCTION]),
            LintViolation(3, 1, Messages[MSG_PROPERTY]),
            LintViolation(5, 1, Messages[MSG_CLASS]),
        )

    @Test
    fun `Property within code block`() =
        assertThatCode(
            """
            public fun codeBlock() {
                val propertyAccessor = "Hello world"
                listOf(1, 2, 3).forEach() {
                    val num = it
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Allow test function`() =
        assertThatCode(
            """
            class AppTest {
                @Test
                fun test() = assertThat("Hello world").isNotEmpty()
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Point to keywords`() =
        assertThatCode(
            """
            /** Hello World. */
            var length: Int = 0

            /** Hello World. */
            fun size(): Int = length

            /** Hello World. */
            var lastIndex: Int
                get() = length - 1

            /** Hello World. */
            class MyList
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 1, Messages[MSG_PROPERTY]),
            LintViolation(5, 1, Messages[MSG_FUNCTION]),
            LintViolation(8, 1, Messages[MSG_PROPERTY]),
            LintViolation(12, 1, Messages[MSG_CLASS]),
        )
}
