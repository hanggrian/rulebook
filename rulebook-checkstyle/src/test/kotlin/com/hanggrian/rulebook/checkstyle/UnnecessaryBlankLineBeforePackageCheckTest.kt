package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class UnnecessaryBlankLineBeforePackageCheckTest : CheckTest() {
    override val check = UnnecessaryBlankLineBeforePackageCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Trimmed file`() = assertAll("UnnecessaryBlankLineBeforePackage1")

    @Test
    fun `Padded file`() =
        assertAll("UnnecessaryBlankLineBeforePackage2", "1: Remove blank line at the beginning.")

    @Test
    fun `Skip comment`() = assertAll("UnnecessaryBlankLineBeforePackage3")
}
