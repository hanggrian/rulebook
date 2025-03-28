package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class UnnecessarySwitchCheckTest {
    private val checker = treeWalkerCheckerOf<UnnecessarySwitchCheck>()

    @Test
    fun `Rule properties`() = UnnecessarySwitchCheck().assertProperties()

    @Test
    fun `Multiple switch branches`() = assertEquals(0, checker.read("UnnecessarySwitch1"))

    @Test
    fun `Single switch branch`() = assertEquals(1, checker.read("UnnecessarySwitch2"))

    @Test
    fun `Skip single branch if it has fall through condition`() =
        assertEquals(0, checker.read("UnnecessarySwitch3"))
}
