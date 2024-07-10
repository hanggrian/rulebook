package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ControlFlowBracingCheckTest {
    private val checker = checkerOf<ControlFlowBracingCheck>()

    @Test
    fun `Rule properties`(): Unit = ControlFlowBracingCheck().assertProperties()

    @Test
    fun `Single-line control flows with bracing`() =
        assertEquals(0, checker.read("ControlFlowBracing1"))

    @Test
    fun `Single-line control flows without bracing`() =
        assertEquals(0, checker.read("ControlFlowBracing2"))

    @Test
    fun `Multiline control flows with bracing`() =
        assertEquals(0, checker.read("ControlFlowBracing3"))

    @Test
    fun `Multiline control flows without bracing`() =
        assertEquals(5, checker.read("ControlFlowBracing4"))

    @Test
    fun `Partially braced if-else statement`() =
        assertEquals(1, checker.read("ControlFlowBracing5"))
}
