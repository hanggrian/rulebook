package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class SpecialFunctionPositionCheckTest {
    private val checker = checkerOf<SpecialFunctionPositionCheck>()

    @Test
    fun `Rule properties`() = SpecialFunctionPositionCheck().assertProperties()

    @Test
    fun `Special function at the bottom`() =
        assertEquals(0, checker.read("SpecialFunctionPosition1"))

    @Test
    fun `Special function not at the bottom`() =
        assertEquals(2, checker.read("SpecialFunctionPosition2"))

    @Test
    fun `Grouped overridden functions`() = assertEquals(0, checker.read("SpecialFunctionPosition3"))

    @Test
    fun `Skip static members`() = assertEquals(0, checker.read("SpecialFunctionPosition4"))
}
