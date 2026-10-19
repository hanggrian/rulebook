package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class AssignmentWrappingCheckTest : CheckTest() {
    override val check = AssignmentWrappingCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single-line assignment`() = assertAll("AssignmentWrapping1")

    @Test
    fun `Multiline assignment with breaking assignee`() =
        assertAll("AssignmentWrapping2", "14:13: Omit newline before '='.")

    @Test
    fun `Multiline assignment with non-breaking assignee`() =
        assertAll("AssignmentWrapping3", "7:19: Put newline before '='.")

    @Test
    fun `Multiline variable but single-line value`() = assertAll("AssignmentWrapping4")

    @Test
    fun `Allow comments after assign operator`() = assertAll("AssignmentWrapping5")

    @Test
    fun `Skip multiline assignment with single-line expression`() = assertAll("AssignmentWrapping6")
}
