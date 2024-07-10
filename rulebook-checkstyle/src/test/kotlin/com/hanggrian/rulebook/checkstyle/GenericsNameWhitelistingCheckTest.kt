package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class GenericsNameWhitelistingCheckTest {
    private val checker = checkerOf<GenericsNameWhitelistingCheck>()

    @Test
    fun `Rule properties`(): Unit = GenericsNameWhitelistingCheck().assertProperties()

    @Test
    fun `Common generic type in class-alike`() =
        assertEquals(0, checker.read("GenericsNameWhitelisting1"))

    @Test
    fun `Uncommon generic type in class-alike`() =
        assertEquals(2, checker.read("GenericsNameWhitelisting2"))

    @Test
    fun `Common generic type in function`() =
        assertEquals(0, checker.read("GenericsNameWhitelisting3"))

    @Test
    fun `Uncommon generic type in function`() =
        assertEquals(1, checker.read("GenericsNameWhitelisting4"))

    @Test
    fun `Skip inner generics`() = assertEquals(0, checker.read("GenericsNameWhitelisting5"))
}
