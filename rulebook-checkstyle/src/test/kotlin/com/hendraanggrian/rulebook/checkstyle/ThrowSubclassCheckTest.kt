package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ThrowSubclassCheckTest {
    private val checker = prepareChecker<ThrowSubclassCheck>()

    @Test
    fun `Throw subclass exceptions`() =
        assertEquals(0, checker.process(prepareFiles("ThrowSubclass1")))

    @Test
    fun `Throw superclass exceptions`() =
        assertEquals(3, checker.process(prepareFiles("ThrowSubclass2")))

    @Test
    fun `Throwing exceptions by reference`() =
        assertEquals(0, checker.process(prepareFiles("ThrowSubclass3")))
}
