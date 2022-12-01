package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.FunctionReturnTypeRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class FunctionReturnTypeRuleTest {
    private val assertThatCode = assertThatRule { FunctionReturnTypeRule() }

    @Test
    fun `Regular function`() = assertThatCode("fun function() { }").hasNoLintViolations()

    @Test
    fun `Abstract function`() = assertThatCode("fun function()").hasNoLintViolations()

    @Test
    fun `Expression function`() = assertThatCode(
        """
        fun expressionFunction() = "Hello world"
        class MyClass {
            fun expressionFunction() = "Hello world"
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 25, ERROR_MESSAGE.format("Expression function")),
        LintViolation(3, 29, ERROR_MESSAGE.format("Expression function"))
    )

    @Test
    fun `Private expression function`() = assertThatCode(
        """
        private fun expressionFunction() = "Hello world"
        private class MyClass {
            fun expressionFunction() = "Hello world"
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Regular property`() =
        assertThatCode("val property = \"Hello world\"").hasNoLintViolations()

    @Test
    fun `Getter function`() = assertThatCode(
        """
        val property get() = "Hello world"
        class Class {
            val property get() = "Hello world"
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 13, ERROR_MESSAGE.format("Property accessor")),
        LintViolation(3, 17, ERROR_MESSAGE.format("Property accessor"))
    )

    @Test
    fun `Private getter function`() = assertThatCode(
        """
        private val property get() = "Hello world"
        private class MyClass {
            val property get() = "Hello world"
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Property within code block`() = assertThatCode(
        """
        fun codeBlock() {
            val property = "Hello world"
            listOf(1, 2, 3).forEach() {
                val num = it
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
