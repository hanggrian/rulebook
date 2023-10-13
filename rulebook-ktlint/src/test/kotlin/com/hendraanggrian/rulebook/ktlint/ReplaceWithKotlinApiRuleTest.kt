package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ReplaceWithKotlinApiRuleTest {
    private val assertThatCode = assertThatRule { ReplaceWithKotlinApiRule() }

    @Test
    fun `Java API in imports`() = assertThatCode("import java.lang.String")
        .hasLintViolationWithoutAutoCorrect(
            1,
            8,
            Messages.get(ReplaceWithKotlinApiRule.MSG_CALL, "kotlin.String")
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
            Messages.get(ReplaceWithKotlinApiRule.MSG_TYPE, "kotlin.String")
        ),
        LintViolation(
            2,
            19,
            Messages.get(ReplaceWithKotlinApiRule.MSG_TYPE, "kotlin.Comparable")
        ),
        LintViolation(
            3,
            24,
            Messages.get(ReplaceWithKotlinApiRule.MSG_TYPE, "kotlin.collections.List")
        )
    )
}
