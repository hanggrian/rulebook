package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class UnnecessaryInitialBlankLineRuleTest {
    private val assertThatCode = assertThatRule { UnnecessaryInitialBlankLineRule() }

    @Test
    fun `Rule properties`() = UnnecessaryInitialBlankLineRule().assertProperties()

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
