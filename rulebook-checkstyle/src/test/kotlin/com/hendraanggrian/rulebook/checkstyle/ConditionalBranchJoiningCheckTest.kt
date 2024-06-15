package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ConditionalBranchJoiningCheckTest {
    private val checker = checkerOf<ConditionalBranchJoiningCheck>()

    @Test
    fun `Rule properties()`(): Unit = ConditionalBranchJoiningCheck().assertProperties()

    @Test
    fun `Joined switch case branches`() = assertEquals(0, checker.read("ConditionalBranchJoining1"))

    @Test
    fun `Separated switch case branches`() =
        assertEquals(1, checker.read("ConditionalBranchJoining2"))
}
