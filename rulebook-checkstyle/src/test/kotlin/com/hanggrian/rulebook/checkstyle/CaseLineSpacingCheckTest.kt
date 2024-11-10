package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CaseLineSpacingCheckTest {
    private val checker = checkerOf<CaseLineSpacingCheck>()

    @Test
    fun `Rule properties`() = CaseLineSpacingCheck().assertProperties()

    @Test
    fun `No line break after single-line branch and line break after multiline branch`() =
        assertEquals(0, checker.read("CaseLineSpacing1"))

    @Test
    fun `Line break after single-line branch`() = assertEquals(1, checker.read("CaseLineSpacing2"))

    @Test
    fun `No line break after multiline branch`() = assertEquals(1, checker.read("CaseLineSpacing3"))
}
