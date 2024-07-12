package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class IfFlatteningCheckTest {
    private val checker = checkerOf<IfFlatteningCheck>()

    @Test
    fun `Rule properties`() = IfFlatteningCheck().assertProperties()

    @Test
    fun `Empty or one statement in if statement`() = assertEquals(0, checker.read("IfFlattening1"))

    @Test
    fun `Invert if with two statements`() = assertEquals(1, checker.read("IfFlattening2"))

    @Test
    fun `Do not invert when there is else`() = assertEquals(0, checker.read("IfFlattening3"))
}
