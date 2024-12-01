package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ChainCallContinuationCheckTest {
    private val checker = checkerOf<ChainCallContinuationCheck>()

    @Test
    fun `Rule properties`() = ChainCallContinuationCheck().assertProperties()

    @Test
    fun `Aligned chain method call continuation`() =
        assertEquals(0, checker.read("ChainCallContinuation1"))

    @Test
    fun `Misaligned chain method call continuation`() =
        assertEquals(2, checker.read("ChainCallContinuation2"))

    @Test
    fun `Inconsistent dot position`() = assertEquals(2, checker.read("ChainCallContinuation3"))

    @Test
    fun `Also capture non-method call`() = assertEquals(1, checker.read("ChainCallContinuation4"))
}
