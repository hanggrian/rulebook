package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NumberSuffixForFloatCheckTest : CheckTest() {
    override val check = NumberSuffixForFloatCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Lowercase literal floats`() = assertAll("NumberSuffixForFloat1")

    @Test
    fun `Uppercase literal floats`() =
        assertAll(
            "NumberSuffixForFloat2",
            "4:17: Tag float literal by f.",
            "7:28: Tag float literal by f.",
        )
}
