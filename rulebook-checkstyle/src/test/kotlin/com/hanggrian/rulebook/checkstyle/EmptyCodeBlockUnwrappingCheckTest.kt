package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class EmptyCodeBlockUnwrappingCheckTest {
    private val checker = checkerOf<EmptyCodeBlockUnwrappingCheck>()

    @Test
    fun `Rule properties`() = EmptyCodeBlockUnwrappingCheck().assertProperties()

    @Test
    fun `Wrapped empty block`() = assertEquals(0, checker.read("EmptyCodeBlockUnwrapping1"))

    @Test
    fun `Unwrapped empty block`() = assertEquals(2, checker.read("EmptyCodeBlockUnwrapping2"))

    @Test
    fun `Control flows with multi-blocks`() =
        assertEquals(0, checker.read("EmptyCodeBlockUnwrapping3"))

    @Test
    fun `Allow code block with comment`() =
        assertEquals(0, checker.read("EmptyCodeBlockUnwrapping4"))
}
