package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class MemberOrderCheckTest {
    private val checker = treeWalkerCheckerOf<MemberOrderCheck>()

    @Test
    fun `Rule properties`() = MemberOrderCheck().assertProperties()

    @Test
    fun `Correct member organizations`() = assertEquals(0, checker.read("MemberOrder1"))

    @Test
    fun `Member property after constructor`() = assertEquals(1, checker.read("MemberOrder2"))

    @Test
    fun `Member constructor after function`() = assertEquals(1, checker.read("MemberOrder3"))

    @Test
    fun `Skip static members`() = assertEquals(0, checker.read("MemberOrder4"))
}
