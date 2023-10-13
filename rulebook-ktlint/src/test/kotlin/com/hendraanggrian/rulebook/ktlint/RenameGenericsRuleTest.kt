package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class RenameGenericsRuleTest {
    private val assertThatCode = assertThatRule { RenameGenericsRule() }

    @Test
    fun `Common generic type in class-alike`() = assertThatCode(
        """
        class MyClass<T>
        annotation class MyAnnotationClass<T>
        data class MyDataClass<T>(val i: Int)
        sealed class MySealedClass<T>
        interface MyInterface<T>
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Uncommon generic type in class-alike`() = assertThatCode(
        """
        class MyClass<X>
        annotation class MyAnnotationClass<X>
        data class MyDataClass<X>(val i: Int)
        sealed class MySealedClass<X>
        interface MyInterface<X>
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 15, Messages[RenameGenericsRule.MSG]),
        LintViolation(2, 36, Messages[RenameGenericsRule.MSG]),
        LintViolation(3, 24, Messages[RenameGenericsRule.MSG]),
        LintViolation(4, 28, Messages[RenameGenericsRule.MSG]),
        LintViolation(5, 23, Messages[RenameGenericsRule.MSG])
    )

    @Test
    fun `Common generic type in function`() = assertThatCode(
        """
        fun <E> execute(list: List<E>) {}
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Uncommon generic type in function`() = assertThatCode(
        """
        fun <X> execute(list: List<X>) {}
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(1, 6, Messages[RenameGenericsRule.MSG])

    @Test
    fun `Reified generic type`() = assertThatCode(
        """
        fun <reified X> execute(list: List<X>) {}
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(1, 14, Messages[RenameGenericsRule.MSG])
}
