package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class UseCommonGenericsCheckTest {
    private val checker = prepareChecker(UseCommonGenericsCheck::class)

    @Test
    fun `Common generic type in class-alike`() =
        assertEquals(0, checker.process(prepareFiles("UseCommonGenerics1")))

    @Test
    fun `Uncommon generic type in class-alike`() =
        assertEquals(2, checker.process(prepareFiles("UseCommonGenerics2")))

    @Test
    fun `Common generic type in function`() =
        assertEquals(0, checker.process(prepareFiles("UseCommonGenerics3")))

    @Test
    fun `Uncommon generic type in function`() =
        assertEquals(1, checker.process(prepareFiles("UseCommonGenerics4")))

    @Test
    fun `Skip inner generics`() =
        assertEquals(0, checker.process(prepareFiles("UseCommonGenerics5")))
}
