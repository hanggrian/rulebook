package com.hendraanggrian.lints.checkstyle.javadoc

import com.hendraanggrian.lints.checkstyle.prepareChecker
import com.hendraanggrian.lints.checkstyle.prepareFiles
import kotlin.test.Test
import kotlin.test.assertEquals

class TagDescriptionPunctuationCheckTest {

    @Test
    fun test() {
        val checker = prepareChecker<TagDescriptionPunctuationCheck>()
        val files = prepareFiles<TagDescriptionPunctuationCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(1, numberOfErrors)
    }
}
