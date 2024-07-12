package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class AssignmentWrappingCheckTest {
    private val checker = checkerOf<AssignmentWrappingCheck>()

    @Test
    fun `Rule properties`() = AssignmentWrappingCheck().assertProperties()

    @Test
    fun `Single-line assignment`() = assertEquals(0, checker.read("AssignmentWrapping1"))

    @Test
    fun `Multiline assignment with breaking assignee`() =
        assertEquals(0, checker.read("AssignmentWrapping2"))

    @Test
    fun `Multiline assignment with non-breaking assignee`() =
        assertEquals(1, checker.read("AssignmentWrapping3"))
}
