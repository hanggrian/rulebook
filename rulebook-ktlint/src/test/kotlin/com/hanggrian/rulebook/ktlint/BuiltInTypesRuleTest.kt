package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BuiltInTypesRuleTest {
    private val assertThatCode = assertThatRule { BuiltInTypesRule() }

    @Test
    fun `Rule properties`() = BuiltInTypesRule().assertProperties()

    @Test
    fun `Kotlin API in imports`() =
        assertThatCode("import kotlin.String")
            .hasNoLintViolations()

    @Test
    fun `Java API in imports`() =
        assertThatCode("import java.lang.String")
            .hasLintViolationWithoutAutoCorrect(1, 8, "Use built-in type 'kotlin.String'.")

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
            LintViolation(1, 11, "Use built-in type 'kotlin.String'."),
            LintViolation(2, 19, "Use built-in type 'kotlin.Comparable'."),
            LintViolation(3, 24, "Use built-in type 'kotlin.collections.List'."),
        )

    @Test
    fun `No Kotlin replacement`() =
        assertThatCode("import java.lang.ResourceBundle")
            .hasNoLintViolations()

    @Test
    fun `Unit test methods`() =
        assertThatCode(
            """
            import org.junit.Test

            @Test
            fun testSomething() {
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 8, "Use built-in type 'kotlin.test.Test'.")
}
