package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
import com.hanggrian.rulebook.ktlint.rules.FileSizeRule.Companion.MAX_FILE_SIZE_PROPERTY
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class FileSizeRuleTest {
    private val assertThatCode = assertThatRule { FileSizeRule() }

    @Test
    fun `Rule properties`() = FileSizeRule().assertProperties()

    @Test
    fun `Small file`() =
        assertThatCode(
            """
            package my.namespace

            import namespace.one.*
            """.trimIndent(),
        ).withEditorConfigOverride(MAX_FILE_SIZE_PROPERTY to 3)
            .hasNoLintViolations()

    @Test
    fun `Large file`() =
        assertThatCode(
            """
            package my.namespace

            import namespace.one.*
            import namespace.two.*
            """.trimIndent(),
        ).withEditorConfigOverride(MAX_FILE_SIZE_PROPERTY to 3)
            .hasLintViolationWithoutAutoCorrect(1, 1, "Reduce file size to '3'.")
}
