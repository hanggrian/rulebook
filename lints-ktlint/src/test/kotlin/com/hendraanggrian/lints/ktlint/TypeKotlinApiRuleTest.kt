package com.hendraanggrian.lints.ktlint

import com.hendraanggrian.lints.ktlint.TypeKotlinApiRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class TypeKotlinApiRuleTest {
    private val assertThatCode = assertThatRule { TypeKotlinApiRule() }

    @Test
    fun `Java API in imports`() = assertThatCode("import java.lang.String")
        .hasLintViolationWithoutAutoCorrect(
            1,
            8,
            ERROR_MESSAGE.format("java.lang.String", "kotlin.String")
        )

    @Test
    fun `Java API in type reference`() = assertThatCode(
        """
        val type: java.lang.String
        val nullableType: java.lang.Comparable?
        val parameterizedType: java.util.List<Int>
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 11, ERROR_MESSAGE.format("java.lang.String", "kotlin.String")),
        LintViolation(
            2,
            19,
            ERROR_MESSAGE.format("java.lang.Comparable", "kotlin.Comparable")
        ),
        LintViolation(3, 24, ERROR_MESSAGE.format("java.util.List", "kotlin.collections.List"))
    )

    @Test
    fun `JUnit API in imports`() = assertThatCode("import org.junit.Test")
        .hasLintViolationWithoutAutoCorrect(
            1,
            8,
            ERROR_MESSAGE.format("org.junit.Test", "kotlin.test.Test")
        )

    @Test
    fun `JUnit API in type reference`() = assertThatCode(
        """
        @org.junit.Before
        fun someTest() { }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(
        1,
        2,
        ERROR_MESSAGE.format("org.junit.Before", "kotlin.test.BeforeTest")
    )
}
