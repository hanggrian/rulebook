package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class ChainCallWrappingCheckTest : CheckTest() {
    override val check = ChainCallWrappingCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Aligned chain method call continuation`() = assertAll("ChainCallWrapping1")

    @Test
    fun `Misaligned chain method call continuation`() =
        assertAll(
            "ChainCallWrapping2",
            "8:13: Omit newline before '.'.",
            "17:13: Omit newline before '.'.",
        )

    @Test
    fun `Inconsistent dot position`() =
        assertAll(
            "ChainCallWrapping3",
            "7:23: Put newline before '.'.",
            "11:14: Put newline before '.'.",
        )

    @Test
    fun `Also capture non-method call`() =
        assertAll("ChainCallWrapping4", "6:19: Put newline before '.'.")

    @Test
    fun `Allow dots on single-line`() = assertAll("ChainCallWrapping5")
}
