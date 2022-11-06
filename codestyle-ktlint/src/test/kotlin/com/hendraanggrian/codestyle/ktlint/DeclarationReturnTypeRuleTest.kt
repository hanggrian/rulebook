package com.hendraanggrian.codestyle.ktlint

import com.hendraanggrian.codestyle.ktlint.DeclarationReturnTypeRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class DeclarationReturnTypeRuleTest {
    private val assertCode = assertThatRule { DeclarationReturnTypeRule() }

    @Test
    fun `Expression function`() {
        assertCode(
            """
                fun function() { }
                fun expressionFunction() = "Hello world"

                class MyClass {
                    fun function() { }
                    fun expressionFunction() = "Hello world"
                }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(2, 1, ERROR_MESSAGE.format("Expression function")),
            LintViolation(6, 5, ERROR_MESSAGE.format("Expression function"))
        )
    }

    @Test
    fun `Property`() {
        assertCode(
            """
                val property = "Hello"
                val propertyAccessor get() = "World"

                class MyClass {
                    val property = "Hello"
                    val propertyAccessor get() = "World"

                    fun doSomething() {
                        val i = 0
                    }
                }
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 1, ERROR_MESSAGE.format("Property")),
            LintViolation(2, 1, ERROR_MESSAGE.format("Property")),
            LintViolation(5, 5, ERROR_MESSAGE.format("Property")),
            LintViolation(6, 5, ERROR_MESSAGE.format("Property"))
        )
    }
}
