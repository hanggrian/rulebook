package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterWrappingCheckTest {
    private val checker = checkerOf<ParameterWrappingCheck>()

    @Test
    fun `Rule properties`() = ParameterWrappingCheck().assertProperties()

    @Test
    fun `Single-line parameters`() = assertEquals(0, checker.read("ParameterWrapping1"))

    @Test
    fun `Multiline parameters each with newline`() =
        assertEquals(0, checker.read("ParameterWrapping2"))

    @Test
    fun `Multiline parameters each without newline`() =
        assertEquals(2, checker.read("ParameterWrapping3"))

    @Test
    fun `Multiline parameters each hugging parenthesis`() =
        assertEquals(4, checker.read("ParameterWrapping4"))
}
