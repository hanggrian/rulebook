package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ClassNameAcronymRuleTest {
    private val assertThatCode = assertThatRule { ClassNameAcronymRule() }

    @Test
    fun `Rule properties`() = ClassNameAcronymRule().assertProperties()

    @Test
    fun `Class names with lowercase abbreviation`() =
        assertThatCode(
            """
            class MySqlClass

            annotation class MySqlAnnotationClass

            data class MySqlDataClass

            sealed class MySqlSealedClass

            interface MySqlInterface

            object MySqlObject
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Class names with uppercase abbreviation`() =
        assertThatCode(
            """
            class MySQLClass

            annotation class MySQLAnnotationClass

            data class MySQLDataClass

            sealed class MySQLSealedClass

            interface MySQLInterface

            object MySQLObject
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 7, "Rename acronym to 'MySqlClass'."),
            LintViolation(3, 18, "Rename acronym to 'MySqlAnnotationClass'."),
            LintViolation(5, 12, "Rename acronym to 'MySqlDataClass'."),
            LintViolation(7, 14, "Rename acronym to 'MySqlSealedClass'."),
            LintViolation(9, 11, "Rename acronym to 'MySqlInterface'."),
            LintViolation(11, 8, "Rename acronym to 'MySqlObject'."),
        )

    @Test
    fun `File abbreviation`() =
        assertThatCode("")
            .asFileWithPath("/some/path/RestAPI.kt")
            .hasLintViolationWithoutAutoCorrect(1, 1, "Rename acronym to 'RestApi'.")

    @Test
    fun `Skip a KTS file`() =
        assertThatCode("class RestAPI")
            .asFileWithPath("/some/path/RestAPI.kts")
            .hasNoLintViolations()
}
