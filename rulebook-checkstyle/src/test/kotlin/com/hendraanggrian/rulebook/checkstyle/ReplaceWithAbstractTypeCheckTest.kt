package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ReplaceWithAbstractTypeCheckTest {
    private val checker = prepareChecker<ReplaceWithAbstractTypeCheck>()

    @Test
    fun `Use collections as function parameter`() =
        assertEquals(0, checker.process(prepareFiles("ReplaceWithAbstractType1")))

    @Test
    fun `Use explicit collections as function parameter`() =
        assertEquals(7, checker.process(prepareFiles("ReplaceWithAbstractType2")))

    @Test
    fun `Use collections as constructor parameter`() =
        assertEquals(0, checker.process(prepareFiles("ReplaceWithAbstractType3")))

    @Test
    fun `Use explicit collections as constructor parameter`() =
        assertEquals(7, checker.process(prepareFiles("ReplaceWithAbstractType4")))
}
