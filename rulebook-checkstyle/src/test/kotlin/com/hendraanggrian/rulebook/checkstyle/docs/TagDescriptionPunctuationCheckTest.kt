package com.hendraanggrian.rulebook.checkstyle.docs

import com.hendraanggrian.rulebook.checkstyle.prepareChecker
import com.hendraanggrian.rulebook.checkstyle.prepareFiles
import kotlin.test.Test
import kotlin.test.assertEquals

class TagDescriptionPunctuationCheckTest {
    @Test
    fun test() {
        val checker = prepareChecker<TagDescriptionPunctuationCheck>()
        val files = prepareFiles<TagDescriptionPunctuationCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(4, numberOfErrors)
    }
}
