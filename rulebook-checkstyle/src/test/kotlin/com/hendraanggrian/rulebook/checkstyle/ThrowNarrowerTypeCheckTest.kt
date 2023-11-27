package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ThrowNarrowerTypeCheckTest {
    private val checker = prepareChecker(ThrowNarrowerTypeCheck::class)

    @Test
    fun `Throw subclass exceptions`() =
        assertEquals(0, checker.process(prepareFiles("ThrowNarrowerType1")))

    @Test
    fun `Throw superclass exceptions`() =
        assertEquals(3, checker.process(prepareFiles("ThrowNarrowerType2")))

    @Test
    fun `Throwing exceptions by reference`() =
        assertEquals(0, checker.process(prepareFiles("ThrowNarrowerType3")))
}
