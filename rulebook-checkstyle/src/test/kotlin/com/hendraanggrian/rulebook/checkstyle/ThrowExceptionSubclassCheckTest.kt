package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ThrowExceptionSubclassCheckTest {
    private val checker = prepareChecker(ThrowExceptionSubclassCheck::class)

    @Test
    fun `Throw subclass exceptions`() =
        assertEquals(0, checker.process(prepareFiles("ThrowExceptionSubclass1")))

    @Test
    fun `Throw superclass exceptions`() =
        assertEquals(3, checker.process(prepareFiles("ThrowExceptionSubclass2")))

    @Test
    fun `Throwing exceptions by reference`() =
        assertEquals(0, checker.process(prepareFiles("ThrowExceptionSubclass3")))
}
