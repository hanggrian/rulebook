package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoLeadingBlankLineCheckTest : CheckTest() {
    override val check = NoLeadingBlankLineCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Trimmed file`() = assertAll("NoLeadingBlankLine1")

    @Test
    fun `Padded file`() = assertAll("NoLeadingBlankLine2", "1: Remove blank line at the beginning.")

    @Test
    fun `Skip comment`() = assertAll("NoLeadingBlankLine3")
}
