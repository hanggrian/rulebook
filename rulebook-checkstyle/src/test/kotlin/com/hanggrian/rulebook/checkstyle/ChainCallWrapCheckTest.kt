package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class ChainCallWrapCheckTest : CheckTest() {
    override val check = ChainCallWrapCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Aligned chain method call continuation`() = assertAll("ChainCallWrap1")

    @Test
    fun `Misaligned chain method call continuation`() =
        assertAll(
            "ChainCallWrap2",
            "8:13: Omit newline before ..",
            "17:13: Omit newline before ..",
        )

    @Test
    fun `Inconsistent dot position`() =
        assertAll(
            "ChainCallWrap3",
            "7:20: Put newline before ..",
            "11:18: Put newline before ..",
        )

    @Test
    fun `Also capture non-method call`() =
        assertAll("ChainCallWrap4", "6:19: Put newline before ..")

    @Test
    fun `Allow single call`() = assertAll("ChainCallWrap5")
}
