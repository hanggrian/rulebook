package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.CapitalizeFirstAcronymLetterRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class CapitalizeFirstAcronymLetterRuleTest {
    private val assertThatCode = assertThatRule { CapitalizeFirstAcronymLetterRule() }

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
            LintViolation(2, 18, Messages.get(MSG, "MySqlAnnotationClass")),
            LintViolation(3, 12, Messages.get(MSG, "MySqlDataClass")),
            LintViolation(4, 14, Messages.get(MSG, "MySqlSealedClass")),
            LintViolation(5, 11, Messages.get(MSG, "MySqlInterface")),
            LintViolation(6, 8, Messages.get(MSG, "MySqlObject")),
        )

    @Test
    fun `Property names with lowercase abbreviation`() =
        assertThatCode(
            """
            val globalProperty = 1

            fun foo() {
                val localProperty = 2
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Property names with uppercase abbreviation`() =
        assertThatCode(
            """
            val globalPROPERTY = 1

            fun foo() {
                val localPROPERTY = 2
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 5, Messages.get(MSG, "globalProperty")),
            LintViolation(4, 9, Messages.get(MSG, "localProperty")),
        )

    @Test
    fun `Function names with lowercase abbreviation`() =
        assertThatCode(
            """
            fun myFunction() {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Function names with uppercase abbreviation`() =
        assertThatCode(
            """
            fun myFUNCTION() {}
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(1, 5, Messages.get(MSG, "myFunction"))

    @Test
    fun `Parameter names with lowercase abbreviation`() =
        assertThatCode(
            """
            class Foo(myParameter: Int)
            fun bar(myParameter: Int) {}
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Parameter names with uppercase abbreviation`() =
        assertThatCode(
            """
            class Foo(myPARAMETER: Int)
            fun bar(myPARAMETER: Int) {}
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 11, Messages.get(MSG, "myParameter")),
            LintViolation(2, 9, Messages.get(MSG, "myParameter")),
        )

    @Test
    fun `Skip static field`() =
        assertThatCode(
            """
            object Foo {
                const val MY_INT = 0
            }
            """.trimIndent(),
        ).hasNoLintViolations()

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
