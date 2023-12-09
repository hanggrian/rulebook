package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ConstructorPositionCheckTest {
    private val checker = prepareChecker(ConstructorPositionCheck::class)

    @Test
    fun `Properties, initializers, constructors, and methods`() =
        assertEquals(0, checker.process(prepareFiles("ConstructorPosition1")))

    @Test
    fun `Property after constructor`() =
        assertEquals(1, checker.process(prepareFiles("ConstructorPosition2")))

    @Test
    fun `Method before constructor`() =
        assertEquals(1, checker.process(prepareFiles("ConstructorPosition3")))
}
