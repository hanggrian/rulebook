package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoUselessContinueCheckTest : CheckTest() {
    override val check = NoUselessContinueCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Loops don't end with continue`() = assertAll("NoUselessContinue1")

    @Test
    fun `Loops end with continue`() =
        assertAll(
            "NoUselessContinue2",
            "7:13: Last 'continue' is not needed.",
            "14:13: Last 'continue' is not needed.",
            "21:13: Last 'continue' is not needed.",
        )

    @Test
    fun `Capture loops without block`() =
        assertAll(
            "NoUselessContinue3",
            "5:32: Last 'continue' is not needed.",
            "9:22: Last 'continue' is not needed.",
            "13:12: Last 'continue' is not needed.",
        )

    @Test
    fun `Capture trailing non-continue`() =
        assertAll(
            "NoUselessContinue4",
            "7:13: Last 'continue' is not needed.",
            "16:13: Last 'continue' is not needed.",
            "25:13: Last 'continue' is not needed.",
        )
}
