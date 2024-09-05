package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultDenestingCheckTest {
    private val checker = checkerOf<DefaultDenestingCheck>()

    @Test
    fun `Rule properties`() = DefaultDenestingCheck().assertProperties()

    @Test
    fun `No throw or return in case`() = assertEquals(0, checker.read("DefaultDenesting1"))

    @Test
    fun `Lift else when case has return`() = assertEquals(1, checker.read("DefaultDenesting2"))

    @Test
    fun `Skip if not all case blocks have return or throw`() =
        assertEquals(0, checker.read("DefaultDenesting3"))
}
