package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class SwitchCaseJoiningCheckTest {
    private val checker = checkerOf<SwitchCaseJoiningCheck>()

    @Test
    fun `Rule properties()`(): Unit = SwitchCaseJoiningCheck().assertProperties()

    @Test
    fun `Joined switch case branches`() = assertEquals(0, checker.read("SwitchCaseJoining1"))

    @Test
    fun `Separated switch case branches`() = assertEquals(1, checker.read("SwitchCaseJoining2"))
}
