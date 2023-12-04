package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class StaticInitializerPositionCheckTest {
    private val checker = prepareChecker(StaticInitializerPositionCheck::class)

    @Test
    fun `Correct format`() =
        assertEquals(0, checker.process(prepareFiles("StaticInitializerPosition1")))

    @Test
    fun `Static initializer before property`() =
        assertEquals(1, checker.process(prepareFiles("StaticInitializerPosition2")))

    @Test
    fun `Static initializer before constructor`() =
        assertEquals(1, checker.process(prepareFiles("StaticInitializerPosition3")))

    @Test
    fun `Static initializer before method`() =
        assertEquals(1, checker.process(prepareFiles("StaticInitializerPosition4")))
}
