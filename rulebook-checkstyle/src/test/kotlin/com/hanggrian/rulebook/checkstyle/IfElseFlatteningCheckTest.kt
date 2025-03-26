package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class IfElseFlatteningCheckTest {
    private val checker = checkerOf<IfElseFlatteningCheck>()

    @Test
    fun `Rule properties`() = IfElseFlatteningCheck().assertProperties()

    @Test
    fun `Empty or one statement in if statement`() =
        assertEquals(0, checker.read("IfElseFlattening1"))

    @Test
    fun `Invert if with multiline statement or two statements`() =
        assertEquals(2, checker.read("IfElseFlattening2"))

    @Test
    fun `Lift else when there is no else if`() = assertEquals(1, checker.read("IfElseFlattening3"))

    @Test
    fun `Skip else if`() = assertEquals(0, checker.read("IfElseFlattening4"))

    @Test
    fun `Skip block with jump statement`() = assertEquals(0, checker.read("IfElseFlattening5"))

    @Test
    fun `Capture trailing non-ifs`() = assertEquals(1, checker.read("IfElseFlattening6"))

    @Test
    fun `Skip recursive if-else because it is not safe to return in inner blocks`() =
        assertEquals(0, checker.read("IfElseFlattening7"))
}
