package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultFlatteningCheckTest {
    private val checker = checkerOf<DefaultFlatteningCheck>()

    @Test
    fun `Rule properties`() = DefaultFlatteningCheck().assertProperties()

    @Test
    fun `No throw or return in case`() = assertEquals(0, checker.read("DefaultFlattening1"))

    @Test
    fun `Lift else when case has return`() = assertEquals(1, checker.read("DefaultFlattening2"))

    @Test
    fun `Skip if not all case blocks have return or throw`() =
        assertEquals(0, checker.read("DefaultFlattening3"))
}
