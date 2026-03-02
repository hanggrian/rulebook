package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class LowercaseHexadecimalCheckTest : CheckTest() {
    override val check = LowercaseHexadecimalCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Lowercase hexadecimal letters`() = assertAll("LowercaseHexadecimal1")

    @Test
    fun `Uppercase hexadecimal letters`() =
        assertAll(
            "LowercaseHexadecimal2",
            "4:17: Use hexadecimal '0x00bb00'.",
            "7:28: Use hexadecimal '0xaa00cc'.",
        )
}
