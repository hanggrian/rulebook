package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class StandardizeStringLiteralCheckTest {
    private val checker = prepareChecker<StandardizeStringLiteralCheck>()

    @Test
    fun `Common color literals`() =
        assertEquals(0, checker.process(prepareFiles("StandardizeStringLiteral1")))

    @Test
    fun `Uncommon color literals`() =
        assertEquals(4, checker.process(prepareFiles("StandardizeStringLiteral2")))

    @Test
    fun `Common encoding literals`() =
        assertEquals(0, checker.process(prepareFiles("StandardizeStringLiteral3")))

    @Test
    fun `Uncommon encoding literals`() =
        assertEquals(2, checker.process(prepareFiles("StandardizeStringLiteral4")))
}
