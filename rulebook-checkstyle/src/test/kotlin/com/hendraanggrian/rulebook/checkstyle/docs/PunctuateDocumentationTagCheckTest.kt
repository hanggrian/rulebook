package com.hendraanggrian.rulebook.checkstyle.docs

import com.hendraanggrian.rulebook.checkstyle.prepareChecker
import com.hendraanggrian.rulebook.checkstyle.prepareFiles
import kotlin.test.Test
import kotlin.test.assertEquals

class PunctuateDocumentationTagCheckTest {
    private val checker = prepareChecker<PunctuateDocumentationTagCheck>()

    @Test
    fun `No description`() =
        assertEquals(0, checker.process(prepareFiles("docs/PunctuateTag1")))

    @Test
    fun `Descriptions end with punctuations`() =
        assertEquals(0, checker.process(prepareFiles("docs/PunctuateTag2")))

    @Test
    fun `Descriptions have no end punctuation`() =
        assertEquals(2, checker.process(prepareFiles("docs/PunctuateTag3")))

    @Test
    fun `Description end with comments`() =
        assertEquals(2, checker.process(prepareFiles("docs/PunctuateTag4")))
}
