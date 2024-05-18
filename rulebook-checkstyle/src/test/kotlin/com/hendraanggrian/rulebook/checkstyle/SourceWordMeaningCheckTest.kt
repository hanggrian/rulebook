package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class SourceWordMeaningCheckTest {
    private val checker = prepareChecker(SourceWordMeaningCheck::class)

    @Test
    fun `Meaningful class names`() =
        assertEquals(0, checker.process(prepareFiles("SourceWordMeaning1")))

    @Test
    fun `Meaningless class names`() =
        assertEquals(4, checker.process(prepareFiles("SourceWordMeaning2")))

    @Test
    fun `Utility class found`() =
        assertEquals(1, checker.process(prepareFiles("SourceWordMeaning3")))
}
