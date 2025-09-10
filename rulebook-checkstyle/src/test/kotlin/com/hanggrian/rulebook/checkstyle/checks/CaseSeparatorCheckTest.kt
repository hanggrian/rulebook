package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class CaseSeparatorCheckTest : CheckTest() {
    override val check = CaseSeparatorCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `No line break after single-line branch and line break after multiline branch`() =
        assertAll("CaseSeparator1")

    @Test
    fun `Line break after single-line branch`() =
        assertAll("CaseSeparator2", "7:22: Remove blank line after single-line branch.")

    @Test
    fun `No line break after multiline branch`() =
        assertAll("CaseSeparator3", "8:17: Add blank line after multiline branch.")

    @Test
    fun `Branches with comment are always multiline`() =
        assertAll(
            "CaseSeparator4",
            "8:22: Add blank line after multiline branch.",
            "11:22: Add blank line after multiline branch.",
            "14:22: Add blank line after multiline branch.",
        )
}
