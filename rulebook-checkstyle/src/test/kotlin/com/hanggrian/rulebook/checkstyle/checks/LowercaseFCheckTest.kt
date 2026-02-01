package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class LowercaseFCheckTest : CheckTest() {
    override val check = LowercaseFCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Lowercase literal floats`() = assertAll("LowercaseF1")

    @Test
    fun `Uppercase literal floats`() =
        assertAll(
            "LowercaseF2",
            "4:17: Tag float literal by f.",
            "7:28: Tag float literal by f.",
        )
}
