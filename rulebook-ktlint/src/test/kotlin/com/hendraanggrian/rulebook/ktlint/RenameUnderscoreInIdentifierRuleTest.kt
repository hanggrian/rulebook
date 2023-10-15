package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.RenameUnderscoreInIdentifierRule.Companion.MSG
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class RenameUnderscoreInIdentifierRuleTest {
    private val assertThatCode = assertThatRule { RenameUnderscoreInIdentifierRule() }

    @Test
    fun `Class names without underscore`() = assertThatCode(
        """
        class MyClass
        annotation class MyAnnotationClass
        data class MyDataClass
        sealed class MySealedClass
        interface MyInterface
        object MyObject
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Class names with underscore`() = assertThatCode(
        """
        class My_Class
        annotation class My_Annotation_Class
        data class My_Data_Class
        sealed class My_Sealed_Class
        interface My_Interface
        object My_Object
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 7, Messages.get(MSG, "MyClass")),
        LintViolation(2, 18, Messages.get(MSG, "MyAnnotationClass")),
        LintViolation(3, 12, Messages.get(MSG, "MyDataClass")),
        LintViolation(4, 14, Messages.get(MSG, "MySealedClass")),
        LintViolation(5, 11, Messages.get(MSG, "MyInterface")),
        LintViolation(6, 8, Messages.get(MSG, "MyObject"))
    )

    @Test
    fun `Property names without underscore`() = assertThatCode(
        """
        val globalProperty = 1

        fun foo() {
            val localProperty = 2
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Property names with underscore`() = assertThatCode(
        """
        val global_property = 1

        fun foo() {
            val local_property = 2
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 5, Messages.get(MSG, "globalProperty")),
        LintViolation(4, 9, Messages.get(MSG, "localProperty"))
    )

    @Test
    fun `Function names without underscore`() = assertThatCode(
        """
        fun myFunction() {}
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Function names with underscore`() = assertThatCode(
        """
        fun my_function() {}
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(1, 5, Messages.get(MSG, "myFunction"))

    @Test
    fun `Parameter names without underscore`() = assertThatCode(
        """
        class Foo(myParameter: Int)
        fun bar(myParameter: Int) {}
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Parameter names with underscore`() = assertThatCode(
        """
        class Foo(my_parameter: Int)
        fun bar(my_parameter: Int) {}
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 11, Messages.get(MSG, "myParameter")),
        LintViolation(2, 9, Messages.get(MSG, "myParameter"))
    )

    @Test
    fun `Skip constants`() = assertThatCode(
        """
        object MyObject {
            const val MY_CONSTANT = 0
        }
        """.trimIndent()
    ).hasNoLintViolations()
}
