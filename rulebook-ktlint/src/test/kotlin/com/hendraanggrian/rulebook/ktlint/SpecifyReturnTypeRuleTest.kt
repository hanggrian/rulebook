package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class SpecifyReturnTypeRuleTest {
    private val assertThatCode = assertThatRule { SpecifyReturnTypeRule() }

    @Test
    fun `Regular function`() = assertThatCode("fun function() {}").hasNoLintViolations()

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
        LintViolation(1, 25, Messages.get(SpecifyReturnTypeRule.MSG_EXPR, "expressionFunction")),
        LintViolation(3, 29, Messages.get(SpecifyReturnTypeRule.MSG_EXPR, "expressionFunction"))
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
    fun `Allow test function`() = assertThatCode(
        """
        class Tester {
            @Test
            fun test() = assertThat("Hello world").isNotEmpty()
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Regular property`() =
        assertThatCode("val property = \"Hello world\"").hasNoLintViolations()

    @Test
    fun `Getter function`() = assertThatCode(
        """
        val propertyAccessor get() = "Hello world"
        class Class {
            val propertyAccessor get() = "Hello world"
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 21, Messages.get(SpecifyReturnTypeRule.MSG_PROP, "propertyAccessor")),
        LintViolation(3, 25, Messages.get(SpecifyReturnTypeRule.MSG_PROP, "propertyAccessor"))
    )

    @Test
    fun `Private getter function`() = assertThatCode(
        """
        private val propertyAccessor get() = "Hello world"
        private class MyClass {
            val propertyAccessor get() = "Hello world"
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Property within code block`() = assertThatCode(
        """
        fun codeBlock() {
            val propertyAccessor = "Hello world"
            listOf(1, 2, 3).forEach() {
                val num = it
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
