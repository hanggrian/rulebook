package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ClassBodyStartingWhitespaceRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ClassBodyStartingWhitespaceRuleTest {
    private val assertThatCode = assertThatRule { ClassBodyStartingWhitespaceRule() }

    @Test
    fun `Class declarations`() = assertThatCode(
        """
        class NoClass
        class ShortClass {

            val property: String = ""
        }
        class LongClass(
            parameter: String
        ) {
            val property: String = ""
        }

        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(4, 5, ERROR_MESSAGE.format("Unexpected", "class")),
        LintViolation(9, 5, ERROR_MESSAGE.format("Missing", "class"))
    )

    @Test
    fun `Annotation class declarations`() = assertThatCode(
        """
        annotation class NoAnnotationClass
        annotation class ShortAnnotationClass {

            // stub
        }
        annotation class LongAnnotationClass(
            val parameter: String
        ) {
            // stub
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(4, 5, ERROR_MESSAGE.format("Unexpected", "class")),
        LintViolation(9, 5, ERROR_MESSAGE.format("Missing", "class"))
    )

    @Test
    fun `Data class declarations`() = assertThatCode(
        """
        data class ShortDataClass(val input: String) {

            val property: String = ""
        }
        data class LongDataClass(
            val parameter: String
        ) {
            val property: String = ""
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(3, 5, ERROR_MESSAGE.format("Unexpected", "class")),
        LintViolation(8, 5, ERROR_MESSAGE.format("Missing", "class"))
    )

    @Test
    fun `Enum class declarations`() = assertThatCode(
        """
        enum class NoEnumClass
        enum class ShortEnumClass {

            ENTRY
        }
        enum class LongEnumClass(
            parameter: String
        ) {
            ENTRY("");
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(4, 5, ERROR_MESSAGE.format("Unexpected", "class")),
        LintViolation(9, 5, ERROR_MESSAGE.format("Missing", "class"))
    )

    @Test
    fun `Sealed class declarations`() = assertThatCode(
        """
        sealed class NoSealedClass
        sealed class ShortSealedClass {

            val property: String = ""
        }
        sealed class LongSealedClass(
            parameter: String
        ) {
            val property: String = ""
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(4, 5, ERROR_MESSAGE.format("Unexpected", "class")),
        LintViolation(9, 5, ERROR_MESSAGE.format("Missing", "class"))
    )

    @Test
    fun `Interface declarations`() = assertThatCode(
        """
        interface NoInterface
        interface ShortInterface {

            val property: String
        }
        interface LongInterface : StubInterface1, StubInterface2, StubInterface3, StubInterface4,
            StubInterface5 {
            val property: String
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(4, 5, ERROR_MESSAGE.format("Unexpected", "interface")),
        LintViolation(8, 5, ERROR_MESSAGE.format("Missing", "interface"))
    )

    @Test
    fun `Object declarations`() = assertThatCode(
        """
        object NoObject
        object ShortObject {

            val property: String = ""
        }
        object LongObject : StubInterface1, StubInterface2, StubInterface3, StubInterface4, StubInterface5,
            StubInterface6 {
            val property: String = ""
        }
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(4, 5, ERROR_MESSAGE.format("Unexpected", "object")),
        LintViolation(8, 5, ERROR_MESSAGE.format("Missing", "object"))
    )
}
