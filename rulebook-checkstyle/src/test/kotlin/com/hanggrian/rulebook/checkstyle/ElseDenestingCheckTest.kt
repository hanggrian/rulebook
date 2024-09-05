package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ElseDenestingCheckTest {
    private val checker = checkerOf<ElseDenestingCheck>()

    @Test
    fun `Rule properties`() = ElseDenestingCheck().assertProperties()

    @Test
    fun `No throw or return in if`() = assertEquals(0, checker.read("ElseDenesting1"))

    @Test
    fun `Lift else when if has return`() = assertEquals(1, checker.read("ElseDenesting2"))

    @Test
    fun `Skip if not all if blocks have return or throw`() =
        assertEquals(0, checker.read("ElseDenesting3"))

    @Test
    fun `Consider if-else without blocks`() = assertEquals(0, checker.read("ElseDenesting4"))
}
