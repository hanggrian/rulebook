package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class DuplicateBlankLineCheckTest : CheckTest() {
    override val check = DuplicateBlankLineCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single empty line`() = assertAll("DuplicateBlankLine1")

    @Test
    fun `Multiple empty lines`() =
        assertAll(
            "DuplicateBlankLine2",
            "3: Remove consecutive blank line.",
            "7: Remove consecutive blank line.",
        )
}
