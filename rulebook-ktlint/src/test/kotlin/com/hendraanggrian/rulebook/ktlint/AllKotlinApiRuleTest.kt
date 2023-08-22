package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class AllKotlinApiRuleTest {
    private val assertThatCode = assertThatRule { AllKotlinApiRule() }

    //region Java
    @Test
    fun `Java API in imports`() = assertThatCode("import java.lang.String")
        .hasLintViolationWithoutAutoCorrect(
            1,
            8,
            Messages.get(AllKotlinApiRule.MSG_CALL, "java.lang.String", "kotlin.String")
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
            Messages.get(AllKotlinApiRule.MSG_TYPE, "java.lang.String", "kotlin.String")
        ),
        LintViolation(
            2,
            19,
            Messages.get(AllKotlinApiRule.MSG_TYPE, "java.lang.Comparable", "kotlin.Comparable")
        ),
        LintViolation(
            3,
            24,
            Messages.get(AllKotlinApiRule.MSG_TYPE, "java.util.List", "kotlin.collections.List")
        )
    )
    //endregion

    //region Junit
    @Test
    fun `JUnit API in imports`() = assertThatCode("import org.junit.Test")
        .hasLintViolationWithoutAutoCorrect(
            1,
            8,
            Messages.get(AllKotlinApiRule.MSG_CALL, "org.junit.Test", "kotlin.test.Test")
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
        Messages.get(AllKotlinApiRule.MSG_TYPE, "org.junit.Before", "kotlin.test.BeforeTest")
    )
    //endregion
}
