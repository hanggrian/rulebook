package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class PunctuateDocumentationTagCheckTest {
    private val checker = prepareChecker<PunctuateDocumentationTagCheck>()

    @Test
    fun `No description`() =
        assertEquals(0, checker.process(prepareFiles("PunctuateDocumentationTag1")))

    @Test
    fun `Descriptions end with punctuations`() =
        assertEquals(0, checker.process(prepareFiles("PunctuateDocumentationTag2")))

    @Test
    fun `Descriptions have no end punctuation`() =
        assertEquals(4, checker.process(prepareFiles("PunctuateDocumentationTag3")))

    @Test
    fun `Description end with comments`() =
        assertEquals(2, checker.process(prepareFiles("PunctuateDocumentationTag4")))
}
