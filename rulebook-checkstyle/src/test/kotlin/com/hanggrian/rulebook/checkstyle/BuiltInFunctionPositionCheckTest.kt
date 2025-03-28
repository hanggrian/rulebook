package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BuiltInFunctionPositionCheckTest {
    private val checker = treeWalkerCheckerOf<BuiltInFunctionPositionCheck>()

    @Test
    fun `Rule properties`() = BuiltInFunctionPositionCheck().assertProperties()

    @Test
    fun `Special function at the bottom`() =
        assertEquals(0, checker.read("BuiltInFunctionPosition1"))

    @Test
    fun `Special function not at the bottom`() =
        assertEquals(2, checker.read("BuiltInFunctionPosition2"))

    @Test
    fun `Grouped overridden functions`() = assertEquals(0, checker.read("BuiltInFunctionPosition3"))

    @Test
    fun `Skip static members`() = assertEquals(0, checker.read("BuiltInFunctionPosition4"))
}
