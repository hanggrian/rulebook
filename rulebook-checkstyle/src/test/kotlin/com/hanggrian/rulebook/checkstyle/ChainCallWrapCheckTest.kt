package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ChainCallWrapCheckTest {
    private val checker = treeWalkerCheckerOf<ChainCallWrapCheck>()

    @Test
    fun `Rule properties`() = ChainCallWrapCheck().assertProperties()

    @Test
    fun `Aligned chain method call continuation`() = assertEquals(0, checker.read("ChainCallWrap1"))

    @Test
    fun `Misaligned chain method call continuation`() =
        assertEquals(2, checker.read("ChainCallWrap2"))

    @Test
    fun `Inconsistent dot position`() = assertEquals(2, checker.read("ChainCallWrap3"))

    @Test
    fun `Also capture non-method call`() = assertEquals(1, checker.read("ChainCallWrap4"))
}
