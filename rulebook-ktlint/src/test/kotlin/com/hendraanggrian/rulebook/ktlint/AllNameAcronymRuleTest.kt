package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.AllNameAcronymRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class AllNameAcronymRuleTest {
    private val assertThatCode = assertThatRule { AllNameAcronymRule() }

    @Test
    fun `Acronym found in a property`() = assertThatCode("val userJSON = {}")
        .asFileWithPath("/some/path/UserJson.kt")
        .hasLintViolationWithoutAutoCorrect(1, 5, ERROR_MESSAGE.format("property"))

    @Test
    fun `Acronym found in a function`() = assertThatCode("fun blendARGB()")
        .asFileWithPath("/some/path/ArgbBlender.kt")
        .hasLintViolationWithoutAutoCorrect(1, 5, ERROR_MESSAGE.format("fun"))

    @Test
    fun `Acronym found in a class`() = assertThatCode("class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 7, ERROR_MESSAGE.format("class"))

    @Test
    fun `Acronym found in an annotation class`() = assertThatCode("annotation class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 18, ERROR_MESSAGE.format("class"))

    @Test
    fun `Acronym found in a data class`() = assertThatCode("data class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 12, ERROR_MESSAGE.format("class"))

    @Test
    fun `Acronym found in a sealed class`() = assertThatCode("sealed class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 14, ERROR_MESSAGE.format("class"))

    @Test
    fun `Acronym found in a interface`() = assertThatCode("interface RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 11, ERROR_MESSAGE.format("interface"))

    @Test
    fun `Acronym found in a object`() = assertThatCode("object RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 8, ERROR_MESSAGE.format("object"))

    @Test
    fun `Acronym found in file`() = assertThatCode("")
        .asFileWithPath("/some/path/RestAPI.kt")
        .hasLintViolationWithoutAutoCorrect(1, 1, ERROR_MESSAGE.format("file"))

    @Test
    fun `Skip a KTS file`() = assertThatCode("class RestAPI")
        .asFileWithPath("/some/path/RestAPI.kts")
        .hasNoLintViolations()
}
