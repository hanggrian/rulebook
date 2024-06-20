package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.FileInitialLineTrimmingRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class FileInitialLineTrimmingRuleTest {
    private val assertThatCode = assertThatRule { FileInitialLineTrimmingRule() }

    @Test
    fun `Rule properties`(): Unit = FileInitialLineTrimmingRule().assertProperties()

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
