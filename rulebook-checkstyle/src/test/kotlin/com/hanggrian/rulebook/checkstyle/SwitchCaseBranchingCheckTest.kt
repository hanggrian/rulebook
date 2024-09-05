package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class SwitchCaseBranchingCheckTest {
    private val checker = checkerOf<SwitchCaseBranchingCheck>()

    @Test
    fun `Rule properties`() = SwitchCaseBranchingCheck().assertProperties()

    @Test
    fun `Multiple switch branches`() = assertEquals(0, checker.read("SwitchCaseBranching1"))

    @Test
    fun `Single switch branch`() = assertEquals(1, checker.read("SwitchCaseBranching2"))
}
