package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class CaseSeparatorCheckTest : CheckTest() {
    override val check = CaseSeparatorCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single-line branches without line break`() = assertAll("CaseSeparator1")

    @Test
    fun `Multiline branches with line break`() = assertAll("CaseSeparator2")

    @Test
    fun `Single-line branches with line break`() =
        assertAll(
            "CaseSeparator3",
            "6:26: Remove blank line after single-line branch.",
            "8:28: Remove blank line after single-line branch.",
        )

    @Test
    fun `Multiline branches without line break`() =
        assertAll(
            "CaseSeparator4",
            "7:22: Add blank line after multiline branch.",
            "10:17: Add blank line after multiline branch.",
        )

    @Test
    fun `Branches with comment are always multiline`() =
        assertAll(
            "CaseSeparator5",
            "8:22: Add blank line after multiline branch.",
            "11:22: Add blank line after multiline branch.",
            "14:22: Add blank line after multiline branch.",
        )
}
