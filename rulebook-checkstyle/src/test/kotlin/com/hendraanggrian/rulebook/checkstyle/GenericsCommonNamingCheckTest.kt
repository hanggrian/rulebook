package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class GenericsCommonNamingCheckTest {
    private val checker = prepareChecker(GenericsCommonNamingCheck::class)

    @Test
    fun `Common generic type in class-alike`() =
        assertEquals(0, checker.process(prepareFiles("GenericsCommonNaming1")))

    @Test
    fun `Uncommon generic type in class-alike`() =
        assertEquals(2, checker.process(prepareFiles("GenericsCommonNaming2")))

    @Test
    fun `Common generic type in function`() =
        assertEquals(0, checker.process(prepareFiles("GenericsCommonNaming3")))

    @Test
    fun `Uncommon generic type in function`() =
        assertEquals(1, checker.process(prepareFiles("GenericsCommonNaming4")))

    @Test
    fun `Skip inner generics`() =
        assertEquals(0, checker.process(prepareFiles("GenericsCommonNaming5")))
}
