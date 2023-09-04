package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class StandardizeStringLiteralCheckTest {
    @Test
    fun test() {
        val checker = prepareChecker<StandardizeStringLiteralCheck>()
        val files = prepareFiles<StandardizeStringLiteralCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(5, numberOfErrors)
    }
}
