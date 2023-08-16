package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class NamesAcronymRuleTest {
    private val assertThatCode = assertThatRule { NamesAcronymRule() }

    //region Property
    @Test
    fun `Property acronym`() = assertThatCode("val userJSON = {}")
        .asFileWithPath("/some/path/UserJson.kt")
        .hasLintViolationWithoutAutoCorrect(
            1,
            5,
            Messages.get(NamesAcronymRule.MSG_OTHERS, "property", "userJSON")
        )
    //endregion

    //region Function and Class
    @Test
    fun `Function acronym`() = assertThatCode("fun blendARGB()")
        .asFileWithPath("/some/path/ArgbBlender.kt")
        .hasLintViolationWithoutAutoCorrect(
            1,
            5,
            Messages.get(NamesAcronymRule.MSG_OTHERS, "fun", "blendARGB")
        )

    @Test
    fun `Class acronym`() = assertThatCode("class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(
            1,
            7,
            Messages.get(NamesAcronymRule.MSG_OTHERS, "class", "RestAPI")
        )

    @Test
    fun `Annotation class acronym`() = assertThatCode("annotation class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(
            1,
            18,
            Messages.get(NamesAcronymRule.MSG_OTHERS, "class", "RestAPI")
        )

    @Test
    fun `Data class acronym`() = assertThatCode("data class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(
            1,
            12,
            Messages.get(NamesAcronymRule.MSG_OTHERS, "class", "RestAPI")
        )

    @Test
    fun `Sealed class acronym`() = assertThatCode("sealed class RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(
            1,
            14,
            Messages.get(NamesAcronymRule.MSG_OTHERS, "class", "RestAPI")
        )

    @Test
    fun `Interface acronym`() = assertThatCode("interface RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(
            1,
            11,
            Messages.get(NamesAcronymRule.MSG_OTHERS, "interface", "RestAPI")
        )

    @Test
    fun `Object acronym`() = assertThatCode("object RestAPI")
        .asFileWithPath("/some/path/RestApi.kt")
        .hasLintViolationWithoutAutoCorrect(
            1,
            8,
            Messages.get(NamesAcronymRule.MSG_OTHERS, "object", "RestAPI")
        )
    //endregion

    //region File
    @Test
    fun `File acronym`() = assertThatCode("")
        .asFileWithPath("/some/path/RestAPI.kt")
        .hasLintViolationWithoutAutoCorrect(1, 1, Messages[NamesAcronymRule.MSG_FILE])

    @Test
    fun `Skip a KTS file`() = assertThatCode("class RestAPI")
        .asFileWithPath("/some/path/RestAPI.kts")
        .hasNoLintViolations()
    //endregion
}
