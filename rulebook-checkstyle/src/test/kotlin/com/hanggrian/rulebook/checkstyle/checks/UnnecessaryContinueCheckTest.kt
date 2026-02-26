package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class UnnecessaryContinueCheckTest : CheckTest() {
    override val check = UnnecessaryContinueCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Loops don't end with continue`() = assertAll("UnnecessaryContinue1")

    @Test
    fun `Loops end with continue`() =
        assertAll(
            "UnnecessaryContinue2",
            "7:13: Last 'continue' is not needed.",
            "14:13: Last 'continue' is not needed.",
            "21:13: Last 'continue' is not needed.",
        )

    @Test
    fun `Capture loops without block`() =
        assertAll(
            "UnnecessaryContinue3",
            "5:32: Last 'continue' is not needed.",
            "9:22: Last 'continue' is not needed.",
            "13:12: Last 'continue' is not needed.",
        )

    @Test
    fun `Capture trailing non-continue`() =
        assertAll(
            "UnnecessaryContinue4",
            "7:13: Last 'continue' is not needed.",
            "16:13: Last 'continue' is not needed.",
            "25:13: Last 'continue' is not needed.",
        )
}
