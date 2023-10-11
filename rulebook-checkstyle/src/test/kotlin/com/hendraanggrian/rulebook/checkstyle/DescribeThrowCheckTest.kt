package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DescribeThrowCheckTest {
    private val checker = prepareChecker<DescribeThrowCheck>()

    @Test
    fun `Throw exceptions with messages`() =
        assertEquals(0, checker.process(prepareFiles("DescribeThrow1")))

    @Test
    fun `Throw exceptions without messages`() =
        assertEquals(3, checker.process(prepareFiles("DescribeThrow2")))

    @Test
    fun `Throw explicit exceptions with messages`() =
        assertEquals(0, checker.process(prepareFiles("DescribeThrow3")))

    @Test
    fun `Throw explicit exceptions without messages`() =
        assertEquals(0, checker.process(prepareFiles("DescribeThrow4")))

    @Test
    fun `Throwing exceptions by reference`() =
        assertEquals(0, checker.process(prepareFiles("DescribeThrow5")))
}
