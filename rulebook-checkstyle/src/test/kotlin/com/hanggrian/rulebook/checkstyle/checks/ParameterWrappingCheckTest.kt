package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class ParameterWrappingCheckTest : CheckTest() {
    override val check = ParameterWrappingCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single-line parameters`() = assertAll("ParameterWrapping1")

    @Test
    fun `Multiline parameters each with newline`() = assertAll("ParameterWrapping2")

    @Test
    fun `Multiline parameters each without newline`() =
        assertAll(
            "ParameterWrapping3",
            "5:19: Break each parameter into newline.",
            "11:30: Break each parameter into newline.",
        )

    @Test
    fun `Aware of chained single-line calls`() = assertAll("ParameterWrapping4")

    @Test
    fun `Allow comments between parameters`() = assertAll("ParameterWrapping5")

    @Test
    fun `Allow SAM`() = assertAll("ParameterWrapping6")
}
