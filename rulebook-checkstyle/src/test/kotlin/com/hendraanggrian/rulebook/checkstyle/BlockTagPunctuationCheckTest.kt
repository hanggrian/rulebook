package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BlockTagPunctuationCheckTest {
    private val checker = prepareChecker(BlockTagPunctuationCheck::class)

    @Test
    fun `No description`() = assertEquals(0, checker.process(prepareFiles("BlockTagPunctuation1")))

    @Test
    fun `Descriptions end with a period`() =
        assertEquals(0, checker.process(prepareFiles("BlockTagPunctuation2")))

    @Test
    fun `Descriptions don't end with a period`() =
        assertEquals(2, checker.process(prepareFiles("BlockTagPunctuation3")))

    @Test
    fun `Long Descriptions`() =
        assertEquals(2, checker.process(prepareFiles("BlockTagPunctuation4")))
}
