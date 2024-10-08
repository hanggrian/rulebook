package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BlockTagOrderingCheckTest {
    private val checker = checkerOf<BlockTagOrderingCheck>()

    @Test
    fun `Rule properties`() = BlockTagOrderingCheck().assertProperties()

    @Test
    fun `Correct tag layout`() = assertEquals(0, checker.read("BlockTagOrdering1"))

    @Test
    fun `Incorrect tag layout`() = assertEquals(2, checker.read("BlockTagOrdering2"))
}
