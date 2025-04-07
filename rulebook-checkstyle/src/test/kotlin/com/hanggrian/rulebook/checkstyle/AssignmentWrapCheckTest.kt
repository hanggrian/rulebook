package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class AssignmentWrapCheckTest : CheckTest() {
    override val check = AssignmentWrapCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single-line assignment`() = assertAll("AssignmentWrap1")

    @Test
    fun `Multiline assignment with breaking assignee`() = assertAll("AssignmentWrap2")

    @Test
    fun `Multiline assignment with non-breaking assignee`() =
        assertAll("AssignmentWrap3", "5:19: Break assignment into newline.")

    @Test
    fun `Multiline variable but single-line value`() = assertAll("AssignmentWrap4")

    @Test
    fun `Skip lambda initializers`() = assertAll("AssignmentWrap5")
}
