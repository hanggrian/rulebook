package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class MultilineControlFlowBracingCheckTest {
    private val checker = checkerOf<MultilineControlFlowBracingCheck>()

    @Test
    fun `Rule properties()`(): Unit = MultilineControlFlowBracingCheck().assertProperties()

    @Test
    fun `Single-line control flows with bracing`() =
        assertEquals(0, checker.read("MultilineControlFlowBracing1"))

    @Test
    fun `Single-line control flows without bracing`() =
        assertEquals(0, checker.read("MultilineControlFlowBracing2"))

    @Test
    fun `Multiline control flows with bracing`() =
        assertEquals(0, checker.read("MultilineControlFlowBracing3"))

    @Test
    fun `Multiline control flows without bracing`() =
        assertEquals(5, checker.read("MultilineControlFlowBracing4"))

    @Test
    fun `Partially braced if-else statement`() =
        assertEquals(1, checker.read("MultilineControlFlowBracing5"))
}
