package com.hendraanggrian.lints.ktlint

import com.hendraanggrian.lints.ktlint.FunctionSpecifyReturnTypeRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class FunctionSpecifyReturnTypeRuleTest {
    private val assertCode = assertThatRule { FunctionSpecifyReturnTypeRule() }

    @Test
    fun `Regular function`() {
        assertCode(
            """
                fun function() { }
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Expression function`() {
        assertCode(
            """
                fun expressionFunction() = "Hello world"
                class MyClass {
                    fun expressionFunction() = "Hello world"
                }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, ERROR_MESSAGE.format("Expression")),
            LintViolation(3, 5, ERROR_MESSAGE.format("Expression"))
        )
    }

    @Test
    fun `Private expression function`() {
        assertCode(
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
        assertCode(
            """
                val property = "Hello world"
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `Getter function`() {
        assertCode(
            """
                val property get() = "Hello world"
                class Class {
                    val property get() = "Hello world"
                }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, ERROR_MESSAGE.format("Getter")),
            LintViolation(3, 5, ERROR_MESSAGE.format("Getter"))
        )
    }

    @Test
    fun `Private getter function`() {
        assertCode(
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
        assertCode(
            """
                fun codeBlock() {
                    val property = "Hello world"
                }
            """.trimIndent()
        ).hasNoLintViolations()
    }
}
