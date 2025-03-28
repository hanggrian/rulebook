package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.ClassNameAcronymRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
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
            LintViolation(1, 7, Messages.get(MSG, "MySqlClass")),
            LintViolation(3, 18, Messages.get(MSG, "MySqlAnnotationClass")),
            LintViolation(5, 12, Messages.get(MSG, "MySqlDataClass")),
            LintViolation(7, 14, Messages.get(MSG, "MySqlSealedClass")),
            LintViolation(9, 11, Messages.get(MSG, "MySqlInterface")),
            LintViolation(11, 8, Messages.get(MSG, "MySqlObject")),
        )

    @Test
    fun `File abbreviation`() =
        assertThatCode("")
            .asFileWithPath("/some/path/RestAPI.kt")
            .hasLintViolationWithoutAutoCorrect(1, 1, Messages.get(MSG, "RestApi"))

    @Test
    fun `Skip a KTS file`() =
        assertThatCode("class RestAPI")
            .asFileWithPath("/some/path/RestAPI.kts")
            .hasNoLintViolations()
}
