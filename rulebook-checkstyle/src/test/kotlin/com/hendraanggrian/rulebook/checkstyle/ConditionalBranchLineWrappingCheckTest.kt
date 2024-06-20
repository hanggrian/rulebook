package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ConditionalBranchLineWrappingCheckTest {
    private val checker = checkerOf<ConditionalBranchLineWrappingCheck>()

    @Test
    fun `Rule properties()`(): Unit = ConditionalBranchLineWrappingCheck().assertProperties()

    @Test
    fun `Joined switch case branches`() =
        assertEquals(0, checker.read("ConditionalBranchLineWrapping1"))

    @Test
    fun `Separated switch case branches`() =
        assertEquals(1, checker.read("ConditionalBranchLineWrapping2"))
}
