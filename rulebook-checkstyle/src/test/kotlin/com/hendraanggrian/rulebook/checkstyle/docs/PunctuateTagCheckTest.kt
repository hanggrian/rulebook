package com.hendraanggrian.rulebook.checkstyle.docs

import com.hendraanggrian.rulebook.checkstyle.prepareChecker
import com.hendraanggrian.rulebook.checkstyle.prepareFiles
import kotlin.test.Test
import kotlin.test.assertEquals

class PunctuateTagCheckTest {
    @Test
    fun test() {
        val checker = prepareChecker<PunctuateTagCheck>()
        val files = prepareFiles<PunctuateTagCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(4, numberOfErrors)
    }
}
