package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class UncommonStringLiteralCheckTest {
    @Test
    fun test() {
        val checker = prepareChecker<UncommonStringLiteralCheck>()
        val files = prepareFiles<UncommonStringLiteralCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(5, numberOfErrors)
    }
}
