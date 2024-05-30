package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class EmptyCodeBlockWrappingCheckTest {
    private val checker = checkerOf<EmptyCodeBlockWrappingCheck>()

    @Test
    fun `Rule properties()`(): Unit = EmptyCodeBlockWrappingCheck().assertProperties()

    @Test
    fun `Wrapped empty class block`() = assertEquals(0, checker.read("EmptyCodeBlockWrapping1"))

    @Test
    fun `Unwrapped empty class block`() = assertEquals(3, checker.read("EmptyCodeBlockWrapping2"))

    @Test
    fun `Wrapped empty function block`() = assertEquals(0, checker.read("EmptyCodeBlockWrapping3"))

    @Test
    fun `Unwrapped empty function block`() =
        assertEquals(3, checker.read("EmptyCodeBlockWrapping4"))
}
