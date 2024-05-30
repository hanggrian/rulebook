package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BlockTagDescriptionPunctuationCheckTest {
    private val checker = checkerOf<BlockTagDescriptionPunctuationCheck>()

    @Test
    fun `Rule properties()`(): Unit = BlockTagDescriptionPunctuationCheck().assertProperties()

    @Test
    fun `No description`() = assertEquals(0, checker.read("BlockTagDescriptionPunctuation1"))

    @Test
    fun `Descriptions end with a period`() =
        assertEquals(0, checker.read("BlockTagDescriptionPunctuation2"))

    @Test
    fun `Descriptions don't end with a period`() =
        assertEquals(2, checker.read("BlockTagDescriptionPunctuation3"))

    @Test
    fun `Long Descriptions`() = assertEquals(2, checker.read("BlockTagDescriptionPunctuation4"))
}
