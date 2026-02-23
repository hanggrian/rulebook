package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class ParenthesesTrimCheckTest : CheckTest() {
    override val check = ParenthesesTrimCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Parentheses without newline padding`() = assertAll("ParenthesesTrim1")

    @Test
    fun `Parentheses with newline padding`() =
        assertAll(
            "ParenthesesTrim2",
            "5: Remove blank line after '('.",
            "7: Remove blank line before ')'.",
            "10: Remove blank line after '('.",
            "12: Remove blank line before ')'.",
        )

    @Test
    fun `Comments are considered content`() = assertAll("ParenthesesTrim3")
}
