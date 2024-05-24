package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.FileInitialWrappingRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class FileInitialWrappingRuleTest {
    private val assertThatCode = assertThatRule { FileInitialWrappingRule() }

    @Test
    fun `Rule properties`(): Unit = FileInitialWrappingRule().assertProperties()

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
}
