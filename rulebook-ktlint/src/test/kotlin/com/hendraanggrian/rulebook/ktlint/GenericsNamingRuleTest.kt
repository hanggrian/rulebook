package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.GenericsNamingRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class GenericsNamingRuleTest {
    private val assertThatCode = assertThatRule { GenericsNamingRule() }

    @Test
    fun `Common generic type in class-alike`() =
        assertThatCode(
            """
            class MyClass<T>
            annotation class MyAnnotationClass<T>
            data class MyDataClass<T>(val i: Int)
            sealed class MySealedClass<T>
            interface MyInterface<T>
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Uncommon generic type in class-alike`() =
        assertThatCode(
            """
            class MyClass<X>
            annotation class MyAnnotationClass<X>
            data class MyDataClass<X>(val i: Int)
            sealed class MySealedClass<X>
            interface MyInterface<X>
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 15, Messages.get(MSG, "E, K, N, T, V")),
            LintViolation(2, 36, Messages.get(MSG, "E, K, N, T, V")),
            LintViolation(3, 24, Messages.get(MSG, "E, K, N, T, V")),
            LintViolation(4, 28, Messages.get(MSG, "E, K, N, T, V")),
            LintViolation(5, 23, Messages.get(MSG, "E, K, N, T, V")),
        )

    @Test
    fun `Common generic type in function`() =
        assertThatCode(
            """
            fun <E> execute(list: List<E>) {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Uncommon generic type in function`() =
        assertThatCode(
            """
            fun <X> execute(list: List<X>) {}
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 6, Messages.get(MSG, "E, K, N, T, V"))

    @Test
    fun `Skip inner generics`() =
        assertThatCode(
            """
            class Foo<T> {
                class Bar<X> {
                }
                fun <Y> bar() {
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Reified generic type`() =
        assertThatCode(
            """
            fun <reified X> execute(list: List<X>) {}
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 14, Messages.get(MSG, "E, K, N, T, V"))
}
