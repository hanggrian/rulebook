package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class LowercaseDCheckTest : CheckTest() {
    override val check = LowercaseDCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Lowercase literal doubles`() = assertAll("LowercaseD1")

    @Test
    fun `Uppercase literal doubles`() =
        assertAll(
            "LowercaseD2",
            "4:17: Tag double literal by d.",
            "7:28: Tag double literal by d.",
        )
}
