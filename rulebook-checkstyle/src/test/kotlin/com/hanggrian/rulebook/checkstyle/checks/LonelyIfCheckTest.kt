package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class LonelyIfCheckTest : CheckTest() {
    override val check = LonelyIfCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `If not in else block`() = assertAll("LonelyIf1")

    @Test
    fun `If in else block`() =
        assertAll("LonelyIf2", "8:13: Replace 'else' with 'else if' condition.")

    @Test
    fun `Capture trailing non-ifs`() =
        assertAll("LonelyIf3", "8:13: Replace 'else' with 'else if' condition.")
}
