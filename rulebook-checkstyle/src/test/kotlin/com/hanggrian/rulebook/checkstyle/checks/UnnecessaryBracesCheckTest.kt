package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class UnnecessaryBracesCheckTest : CheckTest() {
    override val check = UnnecessaryBracesCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `If not in else block`() = assertAll("UnnecessaryBraces1")

    @Test
    fun `If in else block`() =
        assertAll("UnnecessaryBraces2", "7:11: Replace 'else' with 'else if' condition.")
}
