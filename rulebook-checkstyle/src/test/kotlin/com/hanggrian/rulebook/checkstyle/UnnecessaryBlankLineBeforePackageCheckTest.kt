package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class UnnecessaryBlankLineBeforePackageCheckTest {
    private val checker = treeWalkerCheckerOf<UnnecessaryBlankLineBeforePackageCheck>()

    @Test
    fun `Rule properties`() = UnnecessaryBlankLineBeforePackageCheck().assertProperties()

    @Test
    fun `Trimmed file`() = assertEquals(0, checker.read("BlankLineBeforePackage1"))

    @Test
    fun `Padded file`() = assertEquals(1, checker.read("BlankLineBeforePackage2"))

    @Test
    fun `Skip comment`() = assertEquals(0, checker.read("BlankLineBeforePackage3"))
}
