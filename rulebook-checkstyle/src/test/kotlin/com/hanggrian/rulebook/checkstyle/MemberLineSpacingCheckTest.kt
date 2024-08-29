package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class MemberLineSpacingCheckTest {
    private val checker = checkerOf<MemberLineSpacingCheck>()

    @Test
    fun `Rule properties`() = MemberLineSpacingCheck().assertProperties()

    @Test
    fun `Declarations with newline`() = assertEquals(0, checker.read("MemberLineSpacing1"))

    @Test
    fun `Declarations without newline`() = assertEquals(4, checker.read("MemberLineSpacing2"))

    @Test
    fun `Block comment and annotations in declarations`() =
        assertEquals(0, checker.read("MemberLineSpacing3"))

    @Test
    fun `Skip fields`() = assertEquals(0, checker.read("MemberLineSpacing4"))
}
