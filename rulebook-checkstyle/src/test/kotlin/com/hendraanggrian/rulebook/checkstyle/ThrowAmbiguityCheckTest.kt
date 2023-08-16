package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ThrowAmbiguityCheckTest {
    @Test
    fun test() {
        val checker = prepareChecker<ThrowAmbiguityCheck>()
        val files = prepareFiles<ThrowAmbiguityCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(1, numberOfErrors)
    }
}
