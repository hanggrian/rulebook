package com.hanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class UnnecessaryBlankLineBeforePackageRuleTest {
    private val assertThatCode = assertThatRule { UnnecessaryBlankLineBeforePackageRule() }

    @Test
    fun `Rule properties`() = UnnecessaryBlankLineBeforePackageRule().assertProperties()

    @Test
    fun `Trimmed file`() =
        assertThatCode(
            """
            package com.example

            class MyClass
            """.trimIndent(),
        ).asFileWithPath("/some/path/MyClass.kt")
            .hasNoLintViolations()

    @Test
    fun `Padded file`() =
        assertThatCode(
            """

            package com.example

            class MyClass
            """.trimIndent(),
        ).asFileWithPath("/some/path/MyClass.kt")
            .hasLintViolationWithoutAutoCorrect(1, 1, "Remove blank line at the beginning.")

    @Test
    fun `Skip comment`() =
        assertThatCode(
            """
            // Lorem ipsum.

            package com.example

            class MyClass
            """.trimIndent(),
        ).asFileWithPath("/some/path/MyClass.kt")
            .hasNoLintViolations()
}
