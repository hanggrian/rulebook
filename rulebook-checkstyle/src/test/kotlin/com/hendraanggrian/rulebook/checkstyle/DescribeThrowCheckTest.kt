package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DescribeThrowCheckTest {
    @Test
    fun test() {
        val checker = prepareChecker<DescribeThrowCheck>()
        val files = prepareFiles<DescribeThrowCheckTest>()
        val numberOfErrors = checker.process(files)
        assertEquals(1, numberOfErrors)
    }
}
