package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class AddBlankLineInClassRuleTest {
    private val assertThatCode = assertThatRule { AddBlankLineInClassRule() }

    @Test
    fun `Class single-line declaration`() = assertThatCode(
        """
        class MyClass {
            val property = ""
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Class multiple-line declaration`() = assertThatCode(
        """
        class MyClass(
            parameter: String
        ) {
            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(
        4,
        5,
        Messages.get(AddBlankLineInClassRule.MSG, "MyClass")
    )

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

    @Test
    fun `Annotation class single-line declaration`() = assertThatCode(
        """
        annotation class MyAnnotation {
            // stub
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Annotation class multiple-line declaration`() = assertThatCode(
        """
        annotation class MyAnnotation(
            val parameter: String
        ) {
            // stub
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(
        4,
        5,
        Messages.get(AddBlankLineInClassRule.MSG, "MyAnnotation")
    )

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

    @Test
    fun `Data class single-line declaration`() = assertThatCode(
        """
        data class MyData(val parameter: String) {
            val property = ""
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Data class multiple-line declaration`() = assertThatCode(
        """
        data class MyData(
            val parameter: String
        ) {
            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(
        4,
        5,
        Messages.get(AddBlankLineInClassRule.MSG, "MyData")
    )

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

    @Test
    fun `Enum class single-line declaration`() = assertThatCode(
        """
        enum class MyEnum {
            ENTRY
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Enum class multiple-line declaration`() = assertThatCode(
        """
        enum class MyEnum(
            val parameter: String
        ) {
            ENTRY("")
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(
        4,
        5,
        Messages.get(AddBlankLineInClassRule.MSG, "MyEnum")
    )

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

    @Test
    fun `Sealed class single-line declaration`() = assertThatCode(
        """
        sealed class MySealed {
            val property = ""
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Sealed class multiple-line declaration`() = assertThatCode(
        """
        sealed class MySealed(
            parameter: String
        ) {
            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(
        4,
        5,
        Messages.get(AddBlankLineInClassRule.MSG, "MySealed")
    )

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

    @Test
    fun `Interface single-line declaration`() = assertThatCode(
        """
        interface MyInterface {
            val property: String
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Interface multiple-line declaration`() = assertThatCode(
        """
        interface MyInterface
          : Stub {
            val property: String
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(
        3,
        5,
        Messages.get(AddBlankLineInClassRule.MSG, "MyInterface")
    )

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

    @Test
    fun `Object single-line declaration`() = assertThatCode(
        """
        object MyObject {
            val property = ""
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Object multiple-line declaration`() = assertThatCode(
        """
        object MyObject
          : Stub {
            val property = ""
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(
        3,
        5,
        Messages.get(AddBlankLineInClassRule.MSG, "MyObject")
    )

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

    @Test
    fun `Companion object single-line declaration`() = assertThatCode(
        """
        class MyClass {
            companion object {
                val property = ""
            }
        }
        """.trimIndent()
    ).hasNoLintViolations()

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
    ).hasLintViolationWithoutAutoCorrect(
        4,
        9,
        Messages.get(AddBlankLineInClassRule.MSG, "Companion object")
    )

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
}
