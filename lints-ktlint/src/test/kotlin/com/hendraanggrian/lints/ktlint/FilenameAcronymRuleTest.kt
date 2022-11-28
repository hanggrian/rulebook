package com.hendraanggrian.lints.ktlint

import com.hendraanggrian.lints.ktlint.FilenameAcronymRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class FilenameAcronymRuleTest {
    private val assertThatCode = assertThatRule { FilenameAcronymRule() }

    @Test
    fun `Skip a KTS file`() = assertThatCode("class RestAPI")
        .asFileWithPath("/some/path/RestAPI.kts")
        .hasNoLintViolations()

    @Test
    fun `Acronym found`() = assertThatCode("class RestAPI")
        .asFileWithPath("/some/path/RestAPI.kt")
        .hasLintViolationWithoutAutoCorrect(1, 1, ERROR_MESSAGE)
}
