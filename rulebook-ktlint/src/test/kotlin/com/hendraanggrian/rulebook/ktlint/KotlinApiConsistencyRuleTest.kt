package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.KotlinApiConsistencyRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class KotlinApiConsistencyRuleTest {
    private val assertThatCode = assertThatRule { KotlinApiConsistencyRule() }

    @Test
    fun `Kotlin API in imports`() =
        assertThatCode("import kotlin.String")
            .hasNoLintViolations()

    @Test
    fun `Java API in imports`() =
        assertThatCode("import java.lang.String")
            .hasLintViolationWithoutAutoCorrect(1, 8, Messages.get(MSG, "kotlin.String"))

    @Test
    fun `Kotlin API in type reference`() =
        assertThatCode(
            """
            val type: kotlin.String
            val nullableType: kotlin.Comparable?
            val parameterizedType: kotlin.collection.List<Int>
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Java API in type reference`() =
        assertThatCode(
            """
            val type: java.lang.String
            val nullableType: java.lang.Comparable?
            val parameterizedType: java.util.List<Int>
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 11, Messages.get(MSG, "kotlin.String")),
            LintViolation(2, 19, Messages.get(MSG, "kotlin.Comparable")),
            LintViolation(3, 24, Messages.get(MSG, "kotlin.collections.List")),
        )

    @Test
    fun `No Kotlin replacement`() =
        assertThatCode("import java.lang.ResourceBundle")
            .hasNoLintViolations()
}
