package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ReplaceWithKotlinApiRule.Companion.MSG_IMPORT
import com.hendraanggrian.rulebook.ktlint.ReplaceWithKotlinApiRule.Companion.MSG_REFERENCE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ReplaceWithKotlinApiRuleTest {
    private val assertThatCode = assertThatRule { ReplaceWithKotlinApiRule() }

    @Test
    fun `Java API in imports`() = assertThatCode("import java.lang.String")
        .hasLintViolationWithoutAutoCorrect(1, 8, Messages.get(MSG_IMPORT, "kotlin.String"))

    @Test
    fun `Java API in type reference`() = assertThatCode(
        """
        val type: java.lang.String
        val nullableType: java.lang.Comparable?
        val parameterizedType: java.util.List<Int>
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 11, Messages.get(MSG_REFERENCE, "kotlin.String")),
        LintViolation(2, 19, Messages.get(MSG_REFERENCE, "kotlin.Comparable")),
        LintViolation(3, 24, Messages.get(MSG_REFERENCE, "kotlin.collections.List"))
    )
}
