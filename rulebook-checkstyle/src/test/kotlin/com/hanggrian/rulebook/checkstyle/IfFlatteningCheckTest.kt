package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class IfFlatteningCheckTest {
    private val checker = checkerOf<IfFlatteningCheck>()

    @Test
    fun `Rule properties`(): Unit = IfFlatteningCheck().assertProperties()

    @Test
    fun `Empty then statement`() = assertEquals(0, checker.read("IfFlattening1"))

    @Test
    fun `Only 1 line in if statement`() = assertEquals(0, checker.read("IfFlattening2"))

    @Test
    fun `At least 2 lines in if statement`() = assertEquals(1, checker.read("IfFlattening3"))

    @Test
    fun `If statement with else`() = assertEquals(0, checker.read("IfFlattening4"))
}
