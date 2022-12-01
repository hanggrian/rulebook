package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ExceptionAmbiguityCheckTest {

    @Test
    fun test() {
        val checker = prepareChecker<ExceptionAmbiguityCheck>()
        val files = prepareFiles<ExceptionAmbiguityCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(1, numberOfErrors)
    }
}
