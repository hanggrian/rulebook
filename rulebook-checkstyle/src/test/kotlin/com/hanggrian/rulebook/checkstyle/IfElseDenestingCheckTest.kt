package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class IfElseDenestingCheckTest {
    private val checker = checkerOf<IfElseDenestingCheck>()

    @Test
    fun `Rule properties`() = IfElseDenestingCheck().assertProperties()

    @Test
    fun `Empty or one statement in if statement`() =
        assertEquals(0, checker.read("IfElseDenesting1"))

    @Test
    fun `Invert if with multiline statement or two statements`() =
        assertEquals(2, checker.read("IfElseDenesting2"))

    @Test
    fun `Lift else when there is no else if`() = assertEquals(1, checker.read("IfElseDenesting3"))

    @Test
    fun `Skip else if`() = assertEquals(0, checker.read("IfElseDenesting4"))

    @Test
    fun `Capture trailing non-ifs`() = assertEquals(1, checker.read("IfElseDenesting5"))
}
