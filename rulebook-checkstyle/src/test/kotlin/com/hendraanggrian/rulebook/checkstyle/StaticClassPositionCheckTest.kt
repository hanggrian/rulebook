package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class StaticClassPositionCheckTest {
    private val checker = checkerOf<StaticClassPositionCheck>()

    @Test
    fun `Rule properties()`(): Unit = StaticClassPositionCheck().assertProperties()

    @Test
    fun `Static class at the bottom`() = assertEquals(0, checker.read("StaticClassPosition1"))

    @Test
    fun `Static class before property`() = assertEquals(1, checker.read("StaticClassPosition2"))

    @Test
    fun `Static class before constructor`() = assertEquals(1, checker.read("StaticClassPosition3"))

    @Test
    fun `Static class before method`() = assertEquals(1, checker.read("StaticClassPosition3"))
}
