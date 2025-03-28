package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.FileSizeRule.Companion.LIMIT_FILE_SIZE_PROPERTY
import com.hanggrian.rulebook.ktlint.FileSizeRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
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
        ).withEditorConfigOverride(LIMIT_FILE_SIZE_PROPERTY to 3)
            .hasNoLintViolations()

    @Test
    fun `Large file`() =
        assertThatCode(
            """
            package my.namespace

            import namespace.one.*
            import namespace.two.*
            """.trimIndent(),
        ).withEditorConfigOverride(LIMIT_FILE_SIZE_PROPERTY to 3)
            .hasLintViolationWithoutAutoCorrect(1, 1, Messages.get(MSG, 3))
}
