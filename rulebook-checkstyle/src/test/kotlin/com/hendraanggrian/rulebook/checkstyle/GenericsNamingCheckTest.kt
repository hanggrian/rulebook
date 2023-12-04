package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class GenericsNamingCheckTest {
    private val checker = prepareChecker(GenericsNamingCheck::class)

    @Test
    fun `Common generic type in class-alike`() =
        assertEquals(0, checker.process(prepareFiles("GenericsNaming1")))

    @Test
    fun `Uncommon generic type in class-alike`() =
        assertEquals(2, checker.process(prepareFiles("GenericsNaming2")))

    @Test
    fun `Common generic type in function`() =
        assertEquals(0, checker.process(prepareFiles("GenericsNaming3")))

    @Test
    fun `Uncommon generic type in function`() =
        assertEquals(1, checker.process(prepareFiles("GenericsNaming4")))

    @Test
    fun `Skip inner generics`() = assertEquals(0, checker.process(prepareFiles("GenericsNaming5")))
}
