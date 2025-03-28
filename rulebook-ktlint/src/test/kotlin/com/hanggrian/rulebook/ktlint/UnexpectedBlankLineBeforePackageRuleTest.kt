package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.UnexpectedBlankLineBeforePackageRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class UnexpectedBlankLineBeforePackageRuleTest {
    private val assertThatCode = assertThatRule { UnexpectedBlankLineBeforePackageRule() }

    @Test
    fun `Rule properties`() = UnexpectedBlankLineBeforePackageRule().assertProperties()

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
            .hasLintViolationWithoutAutoCorrect(1, 1, Messages[MSG])

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
