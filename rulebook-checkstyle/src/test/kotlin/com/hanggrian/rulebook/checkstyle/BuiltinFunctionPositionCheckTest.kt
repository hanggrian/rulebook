package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BuiltinFunctionPositionCheckTest {
    private val checker = checkerOf<BuiltinFunctionPositionCheck>()

    @Test
    fun `Rule properties`() = BuiltinFunctionPositionCheck().assertProperties()

    @Test
    fun `Special function at the bottom`() =
        assertEquals(0, checker.read("BuiltinFunctionPosition1"))

    @Test
    fun `Special function not at the bottom`() =
        assertEquals(2, checker.read("BuiltinFunctionPosition2"))

    @Test
    fun `Grouped overridden functions`() = assertEquals(0, checker.read("BuiltinFunctionPosition3"))

    @Test
    fun `Skip static members`() = assertEquals(0, checker.read("BuiltinFunctionPosition4"))
}
