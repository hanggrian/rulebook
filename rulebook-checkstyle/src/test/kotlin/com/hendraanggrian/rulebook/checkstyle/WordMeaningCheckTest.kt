package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class WordMeaningCheckTest {
    private val checker = prepareChecker(WordMeaningCheck::class)

    @Test
    fun `Meaningful class names`() = assertEquals(0, checker.process(prepareFiles("WordMeaning1")))

    @Test
    fun `Meaningless class names`() = assertEquals(4, checker.process(prepareFiles("WordMeaning2")))

    @Test
    fun `Violating both ends`() = assertEquals(2, checker.process(prepareFiles("WordMeaning3")))
}
