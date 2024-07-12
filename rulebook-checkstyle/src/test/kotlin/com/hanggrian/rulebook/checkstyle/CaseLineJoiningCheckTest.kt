package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CaseLineJoiningCheckTest {
    private val checker = checkerOf<CaseLineJoiningCheck>()

    @Test
    fun `Rule properties`() = CaseLineJoiningCheck().assertProperties()

    @Test
    fun `Joined switch case branches`() = assertEquals(0, checker.read("CaseLineJoining1"))

    @Test
    fun `Separated switch case branches`() = assertEquals(1, checker.read("CaseLineJoining2"))
}
