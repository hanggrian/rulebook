package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoLonelyIfCheckTest : CheckTest() {
    override val check = NoLonelyIfCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `If not in else block`() = assertAll("NoLonelyIf1")

    @Test
    fun `If in else block`() =
        assertAll("NoLonelyIf2", "8:13: Replace 'else' with 'else if' condition.")

    @Test
    fun `Capture trailing non-ifs`() =
        assertAll("NoLonelyIf3", "8:13: Replace 'else' with 'else if' condition.")
}
