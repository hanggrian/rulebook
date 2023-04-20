package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.ClassBodyStartingWhitespaceRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class ClassBodyStartingWhitespaceRuleTest {
    private val assertThatCode = assertThatRule { ClassBodyStartingWhitespaceRule() }

    //region Class
    @Test
    fun `Class single-line declaration`() = assertThatCode(
        """
        class MyClass {

            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 5, ERROR_MESSAGE.format("Unexpected"))

    @Test
    fun `Class multiple-line declaration`() = assertThatCode(
        """
        class MyClass(
            parameter: String
        ) {
            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(4, 5, ERROR_MESSAGE.format("Missing"))

    @Test
    fun `Class declarations ignore kdoc, comment, and annotation`() = assertThatCode(
        """
        /** With KDoc */
        class MyClass1 {
            val property = ""
        }
        // With comment
        class MyClass2 {
            val property = ""
        }
        @Annotation
        class MyClass3 {
            val property = ""
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion

    //region Annotation Class
    @Test
    fun `Annotation class single-line declaration`() = assertThatCode(
        """
        annotation class MyAnnotation {

            // stub
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 5, ERROR_MESSAGE.format("Unexpected"))

    @Test
    fun `Annotation class multiple-line declaration`() = assertThatCode(
        """
        annotation class MyAnnotation(
            val parameter: String
        ) {
            // stub
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(4, 5, ERROR_MESSAGE.format("Missing"))

    @Test
    fun `Annotation class declarations ignore kdoc, comment, and annotation`() = assertThatCode(
        """
        /** With KDoc */
        annotation class MyAnnotation1 {
            // stub
        }
        // With comment
        annotation class MyAnnotation2 {
            // stub
        }
        @Annotation
        annotation class MyAnnotation3 {
            // stub
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion

    //region Data Class
    @Test
    fun `Data class single-line declaration`() = assertThatCode(
        """
        data class MyData(val parameter: String) {

            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 5, ERROR_MESSAGE.format("Unexpected"))

    @Test
    fun `Data class multiple-line declaration`() = assertThatCode(
        """
        data class MyData(
            val parameter: String
        ) {
            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(4, 5, ERROR_MESSAGE.format("Missing"))

    @Test
    fun `Data class declarations ignore kdoc, comment, and annotation`() = assertThatCode(
        """
        /** With KDoc */
        data class MyData1(val parameter: String) {
            // stub
        }
        // With comment
        data class MyData2(val parameter: String) {
            // stub
        }
        @Annotation
        data class MyData3(val parameter: String) {
            // stub
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion

    //region Enum Class
    @Test
    fun `Enum class single-line declaration`() = assertThatCode(
        """
        enum class MyEnum {

            ENTRY
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 5, ERROR_MESSAGE.format("Unexpected"))

    @Test
    fun `Enum class multiple-line declaration`() = assertThatCode(
        """
        enum class MyEnum(
            val parameter: String
        ) {
            ENTRY("")
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(4, 5, ERROR_MESSAGE.format("Missing"))

    @Test
    fun `Enum class declarations ignore kdoc, comment, and annotation`() = assertThatCode(
        """
        /** With KDoc */
        enum class MyEnum1 {
            // stub
        }
        // With comment
        enum class MyEnum2 {
            // stub
        }
        @Annotation
        enum class MyEnum3 {
            // stub
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion

    //region Sealed Class
    @Test
    fun `Sealed class single-line declaration`() = assertThatCode(
        """
        sealed class MySealed {

            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 5, ERROR_MESSAGE.format("Unexpected"))

    @Test
    fun `Sealed class multiple-line declaration`() = assertThatCode(
        """
        sealed class MySealed(
            parameter: String
        ) {
            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(4, 5, ERROR_MESSAGE.format("Missing"))

    @Test
    fun `Sealed class declarations ignore kdoc, comment, and annotation`() = assertThatCode(
        """
        /** With KDoc */
        sealed class MySealed1 {
            // stub
        }
        // With comment
        enum class MySealed2 {
            // stub
        }
        @Annotation
        enum class MySealed3 {
            // stub
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion

    //region Interface
    @Test
    fun `Interface single-line declaration`() = assertThatCode(
        """
        interface MyInterface {

            val property: String
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 5, ERROR_MESSAGE.format("Unexpected"))

    @Test
    fun `Interface multiple-line declaration`() = assertThatCode(
        """
        interface MyInterface
          : Stub {
            val property: String
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 5, ERROR_MESSAGE.format("Missing"))

    @Test
    fun `Interface declarations ignore kdoc, comment, and annotation`() = assertThatCode(
        """
        /** With KDoc */
        interface MyInterface1 {
            val property: String
        }
        // With comment
        interface MyInterface2 {
            val property: String
        }
        @Annotation
        interface MyInterface3 {
            val property: String
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion

    //region Object
    @Test
    fun `Object single-line declaration`() = assertThatCode(
        """
        object MyObject {

            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 5, ERROR_MESSAGE.format("Unexpected"))

    @Test
    fun `Object multiple-line declaration`() = assertThatCode(
        """
        object MyObject
          : Stub {
            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 5, ERROR_MESSAGE.format("Missing"))

    @Test
    fun `Object declarations ignore kdoc, comment, and annotation`() = assertThatCode(
        """
        /** With KDoc */
        object MyObject1 {
            val property = ""
        }
        // With comment
        object MyObject2 {
            val property = ""
        }
        @Annotation
        object MyObject3 {
            val property = ""
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion

    //region Companion Object
    @Test
    fun `Companion object single-line declaration`() = assertThatCode(
        """
        class MyClass {
            companion object {

                val property = ""
            }
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(4, 9, ERROR_MESSAGE.format("Unexpected"))

    @Test
    fun `Companion object multiple-line declaration`() = assertThatCode(
        """
        class MyClass {
            companion object
              : Stub {
                val property = ""
            }
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(4, 9, ERROR_MESSAGE.format("Missing"))

    @Test
    fun `Companion object declarations ignore kdoc, comment, and annotation`() = assertThatCode(
        """
        class MyClass1 {
            /** With KDoc */
            companion object {
                val property = ""
            }
        }
        class MyClass2 {
            // With comment
            object MyObject2 {
                val property = ""
            }
        }
        class MyClass3 {
            @Annotation
            object MyObject3 {
                val property = ""
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()
    //endregion
}
