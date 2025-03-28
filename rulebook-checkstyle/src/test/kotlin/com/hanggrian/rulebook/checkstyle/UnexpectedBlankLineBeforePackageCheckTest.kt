package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class UnexpectedBlankLineBeforePackageCheckTest {
    private val checker = treeWalkerCheckerOf<UnexpectedBlankLineBeforePackageCheck>()

    @Test
    fun `Rule properties`() = UnexpectedBlankLineBeforePackageCheck().assertProperties()

    @Test
    fun `Trimmed file`() = assertEquals(0, checker.read("BlankLineBeforePackage1"))

    @Test
    fun `Padded file`() = assertEquals(1, checker.read("BlankLineBeforePackage2"))

    @Test
    fun `Skip comment`() = assertEquals(0, checker.read("BlankLineBeforePackage3"))
}
