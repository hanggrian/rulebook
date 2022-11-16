package com.hendraanggrian.lints.checkstyle.javadoc

import com.hendraanggrian.lints.checkstyle.prepareChecker
import com.hendraanggrian.lints.checkstyle.prepareFiles
import kotlin.test.Test
import kotlin.test.assertEquals

class SummaryContinuationFirstWordCheckTest {

    @Test
    fun test() {
        val checker = prepareChecker<SummaryContinuationFirstWordCheck>()
        val files = prepareFiles<SummaryContinuationFirstWordCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(2, numberOfErrors)
    }
}
