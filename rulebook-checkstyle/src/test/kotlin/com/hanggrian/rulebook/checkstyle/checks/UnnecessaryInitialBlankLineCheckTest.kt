package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class UnnecessaryInitialBlankLineCheckTest : CheckTest() {
    override val check = UnnecessaryInitialBlankLineCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Trimmed file`() = assertAll("UnnecessaryInitialBlankLine1")

    @Test
    fun `Padded file`() =
        assertAll("UnnecessaryInitialBlankLine2", "1: Remove blank line at the beginning.")

    @Test
    fun `Skip comment`() = assertAll("UnnecessaryInitialBlankLine3")
}
