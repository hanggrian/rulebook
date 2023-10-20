package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class RenameMeaninglessWordCheckTest {
    private val checker = prepareChecker<RenameMeaninglessWordCheck>()

    @Test
    fun `Meaningful class names`() =
        assertEquals(0, checker.process(prepareFiles("RenameMeaninglessWord1")))

    @Test
    fun `Meaningless class names`() =
        assertEquals(4, checker.process(prepareFiles("RenameMeaninglessWord2")))

    @Test
    fun `Violating both ends`() =
        assertEquals(2, checker.process(prepareFiles("RenameMeaninglessWord3")))
}
