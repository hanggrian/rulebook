package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class UseAbstractCollectionCheckTest {
    @Test
    fun test() {
        val checker = prepareChecker<UseAbstractCollectionCheck>()
        val files = prepareFiles<UseAbstractCollectionCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(5, numberOfErrors)
    }
}
