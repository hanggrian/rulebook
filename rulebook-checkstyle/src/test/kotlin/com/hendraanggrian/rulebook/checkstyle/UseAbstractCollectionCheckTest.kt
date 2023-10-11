package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class UseAbstractCollectionCheckTest {
    private val checker = prepareChecker<UseAbstractCollectionCheck>()

    @Test
    fun `Use collections as function parameter`() =
        assertEquals(0, checker.process(prepareFiles("UseAbstractCollection1")))

    @Test
    fun `Use explicit collections as function parameter`() =
        assertEquals(5, checker.process(prepareFiles("UseAbstractCollection2")))

    @Test
    fun `Use collections as constructor parameter`() =
        assertEquals(0, checker.process(prepareFiles("UseAbstractCollection3")))

    @Test
    fun `Use explicit collections as constructor parameter`() =
        assertEquals(5, checker.process(prepareFiles("UseAbstractCollection4")))
}
