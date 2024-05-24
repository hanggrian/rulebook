package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ConstructorPositionCheckTest {
    private val checker = checkerOf<ConstructorPositionCheck>()

    @Test
    fun `Rule properties()`(): Unit = ConstructorPositionCheck().assertProperties()

    @Test
    fun `Properties, initializers, constructors, and methods`() =
        assertEquals(0, checker.read("ConstructorPosition1"))

    @Test
    fun `Property after constructor`() = assertEquals(1, checker.read("ConstructorPosition2"))

    @Test
    fun `Method before constructor`() = assertEquals(1, checker.read("ConstructorPosition3"))
}
