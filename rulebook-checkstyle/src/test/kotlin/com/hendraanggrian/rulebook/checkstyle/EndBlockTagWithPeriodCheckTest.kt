package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class EndBlockTagWithPeriodCheckTest {
    private val checker = prepareChecker(EndBlockTagWithPeriodCheck::class)

    @Test
    fun `No description`() =
        assertEquals(0, checker.process(prepareFiles("EndBlockTagWithPeriod1")))

    @Test
    fun `Descriptions end with a period`() =
        assertEquals(0, checker.process(prepareFiles("EndBlockTagWithPeriod2")))

    @Test
    fun `Descriptions don't end with a period`() =
        assertEquals(3, checker.process(prepareFiles("EndBlockTagWithPeriod3")))

    @Test
    fun `Long Descriptions`() =
        assertEquals(1, checker.process(prepareFiles("EndBlockTagWithPeriod4")))
}
