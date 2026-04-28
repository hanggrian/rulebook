package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class UnnecessaryLeadingBlankLineCheckTest : CheckTest() {
    override val check = UnnecessaryLeadingBlankLineCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Trimmed file`() = assertAll("UnnecessaryLeadingBlankLine1")

    @Test
    fun `Padded file`() =
        assertAll("UnnecessaryLeadingBlankLine2", "1: Remove blank line at the beginning.")

    @Test
    fun `Skip comment`() = assertAll("UnnecessaryLeadingBlankLine3")
}
