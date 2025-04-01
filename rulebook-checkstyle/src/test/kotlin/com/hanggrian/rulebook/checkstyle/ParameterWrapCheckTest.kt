package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ParameterWrapCheckTest {
    private val checker = treeWalkerCheckerOf<ParameterWrapCheck>()

    @Test
    fun `Rule properties`() = ParameterWrapCheck().assertProperties()

    @Test
    fun `Single-line parameters`() = assertEquals(0, checker.read("ParameterWrap1"))

    @Test
    fun `Multiline parameters each with newline`() = assertEquals(0, checker.read("ParameterWrap2"))

    @Test
    fun `Multiline parameters each without newline`() =
        assertEquals(2, checker.read("ParameterWrap3"))

    @Test
    fun `Multiline parameters each hugging parenthesis`() =
        assertEquals(4, checker.read("ParameterWrap4"))

    @Test
    fun `Aware of chained single-line calls`() = assertEquals(0, checker.read("ParameterWrap5"))
}
