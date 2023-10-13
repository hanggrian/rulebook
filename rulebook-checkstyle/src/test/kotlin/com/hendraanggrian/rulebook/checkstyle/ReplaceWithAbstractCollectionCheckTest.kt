package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ReplaceWithAbstractCollectionCheckTest {
    private val checker = prepareChecker<ReplaceWithAbstractCollectionCheck>()

    @Test
    fun `Use collections as function parameter`() =
        assertEquals(0, checker.process(prepareFiles("ReplaceWithAbstractCollection1")))

    @Test
    fun `Use explicit collections as function parameter`() =
        assertEquals(5, checker.process(prepareFiles("ReplaceWithAbstractCollection2")))

    @Test
    fun `Use collections as constructor parameter`() =
        assertEquals(0, checker.process(prepareFiles("ReplaceWithAbstractCollection3")))

    @Test
    fun `Use explicit collections as constructor parameter`() =
        assertEquals(5, checker.process(prepareFiles("ReplaceWithAbstractCollection4")))
}
