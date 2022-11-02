package com.hendraanggrian.convention.ktlint

import com.hendraanggrian.convention.ktlint.DeclarationReturnTypeRule.Companion.ERROR_MESSAGE
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
                var property = "Hello"
                var propertyAccessor get() = "World"

                class MyClass {
                    var property = "Hello"
                    var propertyAccessor get() = "World"
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
