package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class AssignmentWrapCheckTest : CheckTest() {
    override val check = AssignmentWrapCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single-line assignment`() = assertAll("AssignmentWrap1")

    @Test
    fun `Multiline assignment with breaking assignee`() =
        assertAll("AssignmentWrap2", "14:13: Omit newline before '='.")

    @Test
    fun `Multiline assignment with non-breaking assignee`() =
        assertAll("AssignmentWrap3", "7:19: Put newline before '='.")

    @Test
    fun `Multiline variable but single-line value`() = assertAll("AssignmentWrap4")

    @Test
    fun `Allow comments after assign operator`() = assertAll("AssignmentWrap5")

    @Test
    fun `Skip multiline assignment with single-line expression`() = assertAll("AssignmentWrap6")
}
