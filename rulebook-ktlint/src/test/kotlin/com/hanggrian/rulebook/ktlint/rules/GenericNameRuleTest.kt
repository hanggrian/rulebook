package com.hanggrian.rulebook.ktlint.rules

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
            class MyClass<Type>

            annotation class MyAnnotationClass<GenericValue>

            data class MyDataClass<X>(val i: Int)

            sealed class MySealedClass<K>

            interface MyInterface<V>
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Incorrect generic name in class`() =
        assertThatCode(
            """
            class MyClass<TYPE>

            annotation class MyAnnotationClass<generic_value>

            data class MyDataClass<x>(val i: Int)

            sealed class MySealedClass<k>

            interface MyInterface<vA>
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 15, "Use pascal-case name."),
            LintViolation(3, 36, "Use pascal-case name."),
            LintViolation(5, 24, "Use pascal-case name."),
            LintViolation(7, 28, "Use pascal-case name."),
            LintViolation(9, 23, "Use pascal-case name."),
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
            fun <generic_value> execute(list: List<generic_value>) {}

            fun <reified element> execute2(list: List<element>) {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 6, "Use pascal-case name."),
            LintViolation(3, 14, "Use pascal-case name."),
        )
}
