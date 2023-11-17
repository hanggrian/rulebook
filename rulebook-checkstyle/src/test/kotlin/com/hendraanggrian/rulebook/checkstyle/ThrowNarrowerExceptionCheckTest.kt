package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ThrowNarrowerExceptionCheckTest {
    private val checker = prepareChecker(ThrowNarrowerExceptionCheck::class)

    @Test
    fun `Throw subclass exceptions`() =
        assertEquals(0, checker.process(prepareFiles("ThrowNarrowerException1")))

    @Test
    fun `Throw superclass exceptions`() =
        assertEquals(3, checker.process(prepareFiles("ThrowNarrowerException2")))

    @Test
    fun `Throwing exceptions by reference`() =
        assertEquals(0, checker.process(prepareFiles("ThrowNarrowerException3")))
}
