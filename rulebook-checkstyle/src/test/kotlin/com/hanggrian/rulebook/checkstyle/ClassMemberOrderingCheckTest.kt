package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ClassMemberOrderingCheckTest {
    private val checker = checkerOf<ClassMemberOrderingCheck>()

    @Test
    fun `Rule properties`() = ClassMemberOrderingCheck().assertProperties()

    @Test
    fun `Correct member organizations`() = assertEquals(0, checker.read("ClassMemberOrdering1"))

    @Test
    fun `Member property after constructor`() =
        assertEquals(1, checker.read("ClassMemberOrdering2"))

    @Test
    fun `Member constructor after function`() =
        assertEquals(1, checker.read("ClassMemberOrdering3"))

    @Test
    fun `Skip static members`() = assertEquals(0, checker.read("ClassMemberOrdering4"))
}
