package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NumberSuffixForDoubleCheckTest : CheckTest() {
    override val check = NumberSuffixForDoubleCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Lowercase literal doubles`() = assertAll("NumberSuffixForDouble1")

    @Test
    fun `Uppercase literal doubles`() =
        assertAll(
            "NumberSuffixForDouble2",
            "4:17: Tag double literal by d.",
            "7:28: Tag double literal by d.",
        )
}
