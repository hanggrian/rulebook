package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ElseFlatteningCheckTest {
    private val checker = checkerOf<ElseFlatteningCheck>()

    @Test
    fun `Rule properties`() = ElseFlatteningCheck().assertProperties()

    @Test
    fun `No throw or return in if`() = assertEquals(0, checker.read("ElseFlattening1"))

    @Test
    fun `Lift else when if has return`() = assertEquals(1, checker.read("ElseFlattening2"))

    @Test
    fun `Do not lift when there is else if`() = assertEquals(0, checker.read("ElseFlattening3"))
}
