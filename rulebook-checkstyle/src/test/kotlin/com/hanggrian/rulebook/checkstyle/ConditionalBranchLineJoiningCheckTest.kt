package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class ConditionalBranchLineJoiningCheckTest {
    private val checker = checkerOf<ConditionalBranchLineJoiningCheck>()

    @Test
    fun `Rule properties`(): Unit = ConditionalBranchLineJoiningCheck().assertProperties()

    @Test
    fun `Joined switch case branches`() =
        assertEquals(0, checker.read("ConditionalBranchLineJoining1"))

    @Test
    fun `Separated switch case branches`() =
        assertEquals(1, checker.read("ConditionalBranchLineJoining2"))
}
