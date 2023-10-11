package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class UseKotlinApiRuleTest {
    private val assertThatCode = assertThatRule { UseKotlinApiRule() }

    @Test
    fun `Java API in imports`() = assertThatCode("import java.lang.String")
        .hasLintViolationWithoutAutoCorrect(
            1,
            8,
            Messages.get(UseKotlinApiRule.MSG_CALL, "kotlin.String")
        )

    @Test
    fun `Java API in type reference`() = assertThatCode(
        """
        val type: java.lang.String
        val nullableType: java.lang.Comparable?
        val parameterizedType: java.util.List<Int>
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(
            1,
            11,
            Messages.get(UseKotlinApiRule.MSG_TYPE, "kotlin.String")
        ),
        LintViolation(
            2,
            19,
            Messages.get(UseKotlinApiRule.MSG_TYPE, "kotlin.Comparable")
        ),
        LintViolation(
            3,
            24,
            Messages.get(UseKotlinApiRule.MSG_TYPE, "kotlin.collections.List")
        )
    )

    @Test
    fun `JUnit API in imports`() = assertThatCode("import org.junit.Test")
        .hasLintViolationWithoutAutoCorrect(
            1,
            8,
            Messages.get(UseKotlinApiRule.MSG_CALL, "kotlin.test.Test")
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
        Messages.get(UseKotlinApiRule.MSG_TYPE, "kotlin.test.BeforeTest")
    )
}
