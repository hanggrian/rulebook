package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class StaticClassPositionCheckTest {
    private val checker = prepareChecker(StaticClassPositionCheck::class)

    @Test
    fun `Static class at the bottom`() =
        assertEquals(0, checker.process(prepareFiles("StaticClassPosition1")))

    @Test
    fun `Static class before property`() =
        assertEquals(1, checker.process(prepareFiles("StaticClassPosition2")))

    @Test
    fun `Static class before constructor`() =
        assertEquals(1, checker.process(prepareFiles("StaticClassPosition3")))

    @Test
    fun `Static class before method`() =
        assertEquals(1, checker.process(prepareFiles("StaticClassPosition3")))
}
