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
    fun `Aware of chained single-line calls`() = assertAll("ParameterWrap4")

    @Test
    fun `Allow comments between parameters`() = assertAll("ParameterWrap5")

    @Test
    fun `Allow SAM`() = assertAll("ParameterWrap6")
}
