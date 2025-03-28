package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DuplicateBlankLineCheckTest {
    private val checker = checkerOf<DuplicateBlankLineCheck>()

    @Test
    fun `Rule properties`() = DuplicateBlankLineCheck().assertProperties()

    @Test
    fun `Single empty line`() = assertEquals(0, checker.read("DuplicateBlankLine1"))

    @Test
    fun `Multiple empty lines`() = assertEquals(2, checker.read("DuplicateBlankLine2"))
}
