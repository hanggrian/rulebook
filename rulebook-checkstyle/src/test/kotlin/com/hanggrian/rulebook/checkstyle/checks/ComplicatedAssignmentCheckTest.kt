package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class ComplicatedAssignmentCheckTest : CheckTest() {
    override val check = ComplicatedAssignmentCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Shorthand assignments`() = assertAll("ComplicatedAssignment1")

    @Test
    fun `Complicated assignments`() =
        assertAll(
            "ComplicatedAssignment2",
            "6:13: Use assignment operator '+='.",
            "7:13: Use assignment operator '-='.",
            "8:13: Use assignment operator '*='.",
            "9:13: Use assignment operator '/='.",
            "10:13: Use assignment operator '%='.",
        )

    @Test
    fun `Target leftmost operator`() =
        assertAll(
            "ComplicatedAssignment3",
            "6:13: Use assignment operator '+='.",
        )
}
