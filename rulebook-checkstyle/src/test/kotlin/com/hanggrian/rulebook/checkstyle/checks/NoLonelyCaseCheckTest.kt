package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoLonelyCaseCheckTest : CheckTest() {
    override val check = NoLonelyCaseCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Multiple switch branches`() = assertAll("NoLonelyCase1")

    @Test
    fun `Single switch branch`() =
        assertAll("NoLonelyCase2", "5:9: Replace 'switch' with 'if' condition.")

    @Test
    fun `Skip single branch if it has fall through condition`() = assertAll("NoLonelyCase3")
}
