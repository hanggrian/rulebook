package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.SpecifyTypeExplicitlyRule.Companion.MSG_FUNCTION
import com.hendraanggrian.rulebook.ktlint.SpecifyTypeExplicitlyRule.Companion.MSG_PROPERTY
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.hendraanggrian.rulebook.ktlint.internals.VARIANT_PROPERTY
import com.hendraanggrian.rulebook.ktlint.internals.VariantValue
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class SpecifyTypeExplicitlyRuleTest {
    private val assertThatCode =
        assertThatRule(
            { SpecifyTypeExplicitlyRule() },
            editorConfigProperties = setOf(VARIANT_PROPERTY to VariantValue.library),
        )

    @Test
    fun `Property & function with type`() =
        assertThatCode(
            """
            var length: Int = 0
            fun size(): Int = length
            var lastIndex: Int
                get() = length - 1
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Property & function without type`() =
        assertThatCode(
            """
            var length = 0
            fun size() = length
            var lastIndex
                get() = length - 1
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 11, Messages[MSG_PROPERTY]),
            LintViolation(2, 11, Messages[MSG_FUNCTION]),
            LintViolation(3, 14, Messages[MSG_PROPERTY]),
        )

    @Test
    fun `Non-public modifiers are skipped`() =
        assertThatCode(
            """
            private var length = 0
            private fun size() = length
            private var lastIndex
                get() = length - 1
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Property within code block`() =
        assertThatCode(
            """
            fun codeBlock() {
                val propertyAccessor = "Hello world"
                listOf(1, 2, 3).forEach() {
                    val num = it
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Empty functions`() =
        assertThatCode(
            """
            fun empty() {
            }
            abstract fun init()
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
}
