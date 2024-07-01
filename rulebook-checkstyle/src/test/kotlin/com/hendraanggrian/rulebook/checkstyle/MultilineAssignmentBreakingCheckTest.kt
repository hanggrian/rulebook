package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class MultilineAssignmentBreakingCheckTest {
    private val checker = checkerOf<MultilineAssignmentBreakingCheck>()

    @Test
    fun `Rule properties()`(): Unit = MultilineAssignmentBreakingCheck().assertProperties()

    @Test
    fun `Single-line assignment`() = assertEquals(0, checker.read("MultilineAssignmentBreaking1"))

    @Test
    fun `Multiline assignment with breaking assignee`() =
        assertEquals(0, checker.read("MultilineAssignmentBreaking2"))

    @Test
    fun `Multiline assignment with non-breaking assignee`() =
        assertEquals(1, checker.read("MultilineAssignmentBreaking3"))
}
