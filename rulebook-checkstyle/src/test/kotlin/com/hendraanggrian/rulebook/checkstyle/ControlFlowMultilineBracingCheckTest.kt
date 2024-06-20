package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ControlFlowMultilineBracingCheckTest {
    private val checker = checkerOf<ControlFlowMultilineBracingCheck>()

    @Test
    fun `Rule properties()`(): Unit = ControlFlowMultilineBracingCheck().assertProperties()

    @Test
    fun `Single-line control flows with bracing`() =
        assertEquals(0, checker.read("ControlFlowMultilineBracing1"))

    @Test
    fun `Single-line control flows without bracing`() =
        assertEquals(0, checker.read("ControlFlowMultilineBracing2"))

    @Test
    fun `Multiline control flows with bracing`() =
        assertEquals(0, checker.read("ControlFlowMultilineBracing3"))

    @Test
    fun `Multiline control flows without bracing`() =
        assertEquals(5, checker.read("ControlFlowMultilineBracing4"))

    @Test
    fun `Partially braced if-else statement`() =
        assertEquals(1, checker.read("ControlFlowMultilineBracing5"))
}
