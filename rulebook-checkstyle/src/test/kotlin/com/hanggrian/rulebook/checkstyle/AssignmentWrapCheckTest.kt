package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class AssignmentWrapCheckTest {
    private val checker = treeWalkerCheckerOf<AssignmentWrapCheck>()

    @Test
    fun `Rule properties`() = AssignmentWrapCheck().assertProperties()

    @Test
    fun `Single-line assignment`() = assertEquals(0, checker.read("AssignmentWrap1"))

    @Test
    fun `Multiline assignment with breaking assignee`() =
        assertEquals(0, checker.read("AssignmentWrap2"))

    @Test
    fun `Multiline assignment with non-breaking assignee`() =
        assertEquals(1, checker.read("AssignmentWrap3"))

    @Test
    fun `Multiline variable but single-line value`() =
        assertEquals(0, checker.read("AssignmentWrap4"))

    @Test
    fun `Skip lambda initializers`() = assertEquals(0, checker.read("AssignmentWrap5"))
}
