package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class RedundantIfCheckTest : CheckTest() {
    override val check = RedundantIfCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `If-else do not contain boolean constants`() = assertAll("RedundantIf1")

    @Test
    fun `If-else contain boolean constants`() =
        assertAll(
            "RedundantIf2",
            "5:9: Omit redundant 'if' condition.",
            "13:9: Omit redundant 'if' condition.",
        )

    @Test
    fun `Capture conditions without blocks`() =
        assertAll("RedundantIf3", "5:9: Omit redundant 'if' condition.")

    @Test
    fun `Capture trailing non-ifs`() =
        assertAll("RedundantIf4", "5:9: Omit redundant 'if' condition.")
}
