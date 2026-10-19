package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class ShorthandAssignmentCheckTest : CheckTest() {
    override val check = ShorthandAssignmentCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Shorthand assignments`() = assertAll("ShorthandAssignment1")

    @Test
    fun `Complicated assignments`() =
        assertAll(
            "ShorthandAssignment2",
            "6:13: Use assignment operator '+='.",
            "7:13: Use assignment operator '-='.",
            "8:13: Use assignment operator '*='.",
            "9:13: Use assignment operator '/='.",
            "10:13: Use assignment operator '%='.",
        )

    @Test
    fun `Target leftmost operator`() =
        assertAll(
            "ShorthandAssignment3",
            "6:13: Use assignment operator '+='.",
        )
}
