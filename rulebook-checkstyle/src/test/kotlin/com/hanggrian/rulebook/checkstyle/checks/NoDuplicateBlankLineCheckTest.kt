package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoDuplicateBlankLineCheckTest : CheckTest() {
    override val check = NoDuplicateBlankLineCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single empty line`() = assertAll("NoDuplicateBlankLine1")

    @Test
    fun `Multiple empty lines`() =
        assertAll(
            "NoDuplicateBlankLine2",
            "3: Remove consecutive blank line.",
            "7: Remove consecutive blank line.",
        )
}
