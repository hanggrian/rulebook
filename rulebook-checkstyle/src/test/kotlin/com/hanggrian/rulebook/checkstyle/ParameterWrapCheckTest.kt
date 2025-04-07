package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class ParameterWrapCheckTest : CheckTest() {
    override val check = ParameterWrapCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single-line parameters`() = assertAll("ParameterWrap1")

    @Test
    fun `Multiline parameters each with newline`() = assertAll("ParameterWrap2")

    @Test
    fun `Multiline parameters each without newline`() =
        assertAll(
            "ParameterWrap3",
            "5:19: Break each parameter into newline.",
            "11:30: Break each parameter into newline.",
        )

    @Test
    fun `Multiline parameters each hugging parenthesis`() =
        assertAll(
            "ParameterWrap4",
            "4:13: Do not join parentheses with parameters.",
            "5:20: Do not join parentheses with parameters.",
            "8:12: Do not join parentheses with parameters.",
            "9:14: Do not join parentheses with parameters.",
        )

    @Test
    fun `Aware of chained single-line calls`() = assertAll("ParameterWrap5")

    @Test
    fun `Allow comments between parameters`() = assertAll("ParameterWrap6")

    @Test
    fun `Allow SAM`() = assertAll("ParameterWrap7")
}
