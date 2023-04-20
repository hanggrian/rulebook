package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.AllNameAcronymRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class AllNameAcronymRuleTest {
    private val assertThatCode = assertThatRule { AllNameAcronymRule() }

    //region Property
    @Test
    fun `Property acronym`() = assertThatCode("val userJSON = {}")
        .asFileWithPath("/some/path/UserJson.kt")
        .hasLintViolationWithoutAutoCorrect(1, 5, ERROR_MESSAGE.format("property"))
    //endregion

    //region Function and Class
    @Test
    fun `Function acronym`() = assertThatCode("fun blendARGB()")
        .asFileWithPath("/some/path/ArgbBlender.kt")
        .hasLintViolationWithoutAutoCorrect(1, 5, ERROR_MESSAGE.format("fun"))

    @Test
    fun `Class acronym`() = assertThatCode("class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 7, ERROR_MESSAGE.format("class"))

    @Test
    fun `Annotation class acronym`() = assertThatCode("annotation class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 18, ERROR_MESSAGE.format("class"))

    @Test
    fun `Data class acronym`() = assertThatCode("data class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 12, ERROR_MESSAGE.format("class"))

    @Test
    fun `Sealed class acronym`() = assertThatCode("sealed class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 14, ERROR_MESSAGE.format("class"))

    @Test
    fun `Interface acronym`() = assertThatCode("interface RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 11, ERROR_MESSAGE.format("interface"))

    @Test
    fun `Object acronym`() = assertThatCode("object RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(1, 8, ERROR_MESSAGE.format("object"))
    //endregion

    //region File
    @Test
    fun `File acronym`() = assertThatCode("")
        .asFileWithPath("/some/path/RestAPI.kt")
        .hasLintViolationWithoutAutoCorrect(1, 1, ERROR_MESSAGE.format("file"))

    @Test
    fun `Skip a KTS file`() = assertThatCode("class RestAPI")
        .asFileWithPath("/some/path/RestAPI.kts")
        .hasNoLintViolations()
    //endregion
}
