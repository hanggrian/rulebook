package com.hendraanggrian.lints.ktlint

import com.hendraanggrian.lints.ktlint.FunctionSpecifyReturnTypeRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class FunctionSpecifyReturnTypeRuleTest {
    private val assertThatCode = assertThatRule { FunctionSpecifyReturnTypeRule() }

    @Test
    fun `Regular function`() {
        assertThatCode(
            """
                fun function() { }
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Expression function`() {
        assertThatCode(
            """
                fun expressionFunction() = "Hello world"
                class MyClass {
                    fun expressionFunction() = "Hello world"
                }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 25, ERROR_MESSAGE.format("Expression")),
            LintViolation(3, 29, ERROR_MESSAGE.format("Expression"))
        )
    }

    @Test
    fun `Private expression function`() {
        assertThatCode(
            """
                private fun expressionFunction() = "Hello world"
                private class MyClass {
                    fun expressionFunction() = "Hello world"
                }
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Regular Property`() {
        assertThatCode(
            """
                val property = "Hello world"
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Getter function`() {
        assertThatCode(
            """
                val property get() = "Hello world"
                class Class {
                    val property get() = "Hello world"
                }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 13, ERROR_MESSAGE.format("Getter")),
            LintViolation(3, 17, ERROR_MESSAGE.format("Getter"))
        )
    }

    @Test
    fun `Private getter function`() {
        assertThatCode(
            """
                private val property get() = "Hello world"
                private class MyClass {
                    val property get() = "Hello world"
                }
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Property within code block`() {
        assertThatCode(
            """
                fun codeBlock() {
                    val property = "Hello world"
                }
            """.trimIndent()
        ).hasNoLintViolations()
    }
}
