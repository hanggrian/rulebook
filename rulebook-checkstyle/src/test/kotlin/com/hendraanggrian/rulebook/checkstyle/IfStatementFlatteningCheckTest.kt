package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class IfStatementFlatteningCheckTest {
    private val checker = checkerOf<IfStatementFlatteningCheck>()

    @Test
    fun `Rule properties()`(): Unit = IfStatementFlatteningCheck().assertProperties()

    @Test
    fun `Empty then statement`() = assertEquals(0, checker.read("IfStatementFlattening1"))

    @Test
    fun `Only 1 line in if statement`() = assertEquals(0, checker.read("IfStatementFlattening2"))

    @Test
    fun `At least 2 lines in if statement`() =
        assertEquals(1, checker.read("IfStatementFlattening3"))

    @Test
    fun `If statement with else`() = assertEquals(0, checker.read("IfStatementFlattening4"))
}
