package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class LowercaseHexCheckTest : CheckTest() {
    override val check = LowercaseHexCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Lowercase hexadecimal letters`() = assertAll("LowercaseHex1")

    @Test
    fun `Uppercase hexadecimal letters`() =
        assertAll(
            "LowercaseHex2",
            "4:17: Use hexadecimal '0x00bb00'.",
            "7:28: Use hexadecimal '0xaa00cc'.",
        )
}
