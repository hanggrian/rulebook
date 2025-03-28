package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CaseSeparatorCheckTest {
    private val checker = treeWalkerCheckerOf<CaseSeparatorCheck>()

    @Test
    fun `Rule properties`() = CaseSeparatorCheck().assertProperties()

    @Test
    fun `No line break after single-line branch and line break after multiline branch`() =
        assertEquals(0, checker.read("CaseSeparator1"))

    @Test
    fun `Line break after single-line branch`() = assertEquals(1, checker.read("CaseSeparator2"))

    @Test
    fun `No line break after multiline branch`() = assertEquals(1, checker.read("CaseSeparator3"))

    @Test
    fun `Branches with comment are always multiline`() =
        assertEquals(3, checker.read("CaseSeparator4"))
}
