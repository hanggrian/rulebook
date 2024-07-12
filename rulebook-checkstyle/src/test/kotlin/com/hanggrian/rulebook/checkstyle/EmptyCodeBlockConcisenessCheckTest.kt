package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class EmptyCodeBlockConcisenessCheckTest {
    private val checker = checkerOf<EmptyCodeBlockConcisenessCheck>()

    @Test
    fun `Rule properties`() = EmptyCodeBlockConcisenessCheck().assertProperties()

    @Test
    fun `Wrapped empty class block`() = assertEquals(0, checker.read("EmptyCodeBlockConciseness1"))

    @Test
    fun `Unwrapped empty class block`() =
        assertEquals(3, checker.read("EmptyCodeBlockConciseness2"))

    @Test
    fun `Wrapped empty function block`() =
        assertEquals(0, checker.read("EmptyCodeBlockConciseness3"))

    @Test
    fun `Unwrapped empty function block`() =
        assertEquals(3, checker.read("EmptyCodeBlockConciseness4"))
}
