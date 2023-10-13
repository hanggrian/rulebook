package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class RenameGenericsCheckTest {
    private val checker = prepareChecker<RenameGenericsCheck>()

    @Test
    fun `Common generic type in class-alike`() =
        assertEquals(0, checker.process(prepareFiles("RenameGenerics1")))

    @Test
    fun `Uncommon generic type in class-alike`() =
        assertEquals(2, checker.process(prepareFiles("RenameGenerics2")))

    @Test
    fun `Common generic type in function`() =
        assertEquals(0, checker.process(prepareFiles("RenameGenerics3")))

    @Test
    fun `Uncommon generic type in function`() =
        assertEquals(1, checker.process(prepareFiles("RenameGenerics4")))
}
