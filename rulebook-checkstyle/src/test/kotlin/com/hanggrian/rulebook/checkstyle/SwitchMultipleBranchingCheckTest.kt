package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class SwitchMultipleBranchingCheckTest {
    private val checker = checkerOf<SwitchMultipleBranchingCheck>()

    @Test
    fun `Rule properties`() = SwitchMultipleBranchingCheck().assertProperties()

    @Test
    fun `Multiple switch branches`() = assertEquals(0, checker.read("SwitchMultipleBranching1"))

    @Test
    fun `Single switch branch`() = assertEquals(1, checker.read("SwitchMultipleBranching2"))
}
