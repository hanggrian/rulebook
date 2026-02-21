package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class GenericNameRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { GenericNameRule() }

    @Test
    fun `Rule properties`() = GenericNameRule().assertProperties()

    @Test
    fun `Correct generic name in class`() =
        assertThatCode(
            """
            class MyClass<T>

            annotation class MyAnnotationClass<E>

            data class MyDataClass<X>(val i: Int)

            sealed class MySealedClass<K>

            interface MyInterface<V>
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Incorrect generic name in class`() =
        assertThatCode(
            """
            class MyClass<XA>

            annotation class MyAnnotationClass<Xa>

            data class MyDataClass<aX>(val i: Int)

            sealed class MySealedClass<a_x>

            interface MyInterface<A_X>
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 15, "Use single uppercase letter."),
            LintViolation(3, 36, "Use single uppercase letter."),
            LintViolation(5, 24, "Use single uppercase letter."),
            LintViolation(7, 28, "Use single uppercase letter."),
            LintViolation(9, 23, "Use single uppercase letter."),
        )

    @Test
    fun `Correct generic name in function`() =
        assertThatCode(
            """
            fun <E> execute(list: List<E>) {}

            fun <reified E> execute2(list: List<E>) {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Incorrect generic name in function`() =
        assertThatCode(
            """
            fun <Xa> execute(list: List<Xa>) {}

            fun <reified aX> execute2(list: List<aX>) {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 6, "Use single uppercase letter."),
            LintViolation(3, 14, "Use single uppercase letter."),
        )

    @Test
    fun `Skip multiple generics`() =
        assertThatCode(
            """
            class Foo<Xa, Ax>

            fun <Bar, Baz> bar(list: List<Bar, Baz>) {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Skip inner generics`() =
        assertThatCode(
            """
            class Foo<T> {
                class Bar<X> {}

                fun <Y> bar() {}
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
