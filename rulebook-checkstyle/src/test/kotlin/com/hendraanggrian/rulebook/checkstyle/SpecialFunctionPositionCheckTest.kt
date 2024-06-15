package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class SpecialFunctionPositionCheckTest {
    private val checker = checkerOf<SpecialFunctionPositionCheck>()

    @Test
    fun `Rule properties()`(): Unit = SpecialFunctionPositionCheck().assertProperties()

    @Test
    fun `Overridden function at the bottom`() =
        assertEquals(0, checker.read("SpecialFunctionPosition1"))

    @Test
    fun `Overridden class before function`() =
        assertEquals(1, checker.read("SpecialFunctionPosition2"))

    @Test
    fun `Grouping overridden functions`() =
        assertEquals(0, checker.read("SpecialFunctionPosition3"))

    @Test
    fun `Skip static members`() = assertEquals(0, checker.read("SpecialFunctionPosition4"))
}
