package com.hendraanggrian.lints.checkstyle.javadoc

import com.hendraanggrian.lints.checkstyle.prepareChecker
import com.hendraanggrian.lints.checkstyle.prepareFiles
import kotlin.test.Test
import kotlin.test.assertEquals

class ParagraphContinuationFirstWordCheckTest {

    @Test
    fun test() {
        val checker = prepareChecker<ParagraphContinuationFirstWordCheck>()
        val files = prepareFiles<ParagraphContinuationFirstWordCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(2, numberOfErrors)
    }
}
