package com.hendraanggrian.rulebook.checkstyle.docs

import com.hendraanggrian.rulebook.checkstyle.prepareChecker
import com.hendraanggrian.rulebook.checkstyle.prepareFiles
import kotlin.test.Test
import kotlin.test.assertEquals

class TagDescriptionSentenceCheckTest {
    @Test
    fun test() {
        val checker = prepareChecker<TagDescriptionSentenceCheck>()
        val files = prepareFiles<TagDescriptionSentenceCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(1, numberOfErrors)
    }
}
