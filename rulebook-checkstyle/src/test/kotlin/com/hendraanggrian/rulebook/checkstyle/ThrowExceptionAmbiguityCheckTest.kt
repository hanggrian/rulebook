package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ThrowExceptionAmbiguityCheckTest {
    @Test
    fun test() {
        val checker = prepareChecker<ThrowExceptionAmbiguityCheck>()
        val files = prepareFiles<ThrowExceptionAmbiguityCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(1, numberOfErrors)
    }
}
