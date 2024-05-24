package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BlockTagPunctuationCheckTest {
    private val checker = checkerOf<BlockTagPunctuationCheck>()

    @Test
    fun `Rule properties()`(): Unit = BlockTagPunctuationCheck().assertProperties()

    @Test
    fun `No description`() = assertEquals(0, checker.read("BlockTagPunctuation1"))

    @Test
    fun `Descriptions end with a period`() = assertEquals(0, checker.read("BlockTagPunctuation2"))

    @Test
    fun `Descriptions don't end with a period`() =
        assertEquals(2, checker.read("BlockTagPunctuation3"))

    @Test
    fun `Long Descriptions`() = assertEquals(2, checker.read("BlockTagPunctuation4"))
}
