package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class RenameUncommonGenericsCheckTest {
    private val checker = prepareChecker<RenameUncommonGenericsCheck>()

    @Test
    fun `Common generic type in class-alike`() =
        assertEquals(0, checker.process(prepareFiles("RenameUncommonGenerics1")))

    @Test
    fun `Uncommon generic type in class-alike`() =
        assertEquals(2, checker.process(prepareFiles("RenameUncommonGenerics2")))

    @Test
    fun `Common generic type in function`() =
        assertEquals(0, checker.process(prepareFiles("RenameUncommonGenerics3")))

    @Test
    fun `Uncommon generic type in function`() =
        assertEquals(1, checker.process(prepareFiles("RenameUncommonGenerics4")))
}
