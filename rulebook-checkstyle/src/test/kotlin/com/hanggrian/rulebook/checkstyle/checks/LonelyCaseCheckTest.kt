package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class LonelyCaseCheckTest : CheckTest() {
    override val check = LonelyCaseCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Multiple switch branches`() = assertAll("LonelyCase1")

    @Test
    fun `Single switch branch`() =
        assertAll("LonelyCase2", "5:9: Replace 'switch' with 'if' condition.")

    @Test
    fun `Skip single branch if it has fall through condition`() = assertAll("LonelyCase3")
}
