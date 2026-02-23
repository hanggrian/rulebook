package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class UnnecessarySwitchCheckTest : CheckTest() {
    override val check = UnnecessarySwitchCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Multiple switch branches`() = assertAll("UnnecessarySwitch1")

    @Test
    fun `Single switch branch`() =
        assertAll("UnnecessarySwitch2", "5:9: Replace 'switch' with 'if' condition.")

    @Test
    fun `Skip single branch if it has fall through condition`() = assertAll("UnnecessarySwitch3")
}
