package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class CodeBlockTrimCheckTest : CheckTest() {
    override val check = CodeBlockTrimCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Code blocks without newline padding`() = assertAll("CodeBlockTrim1")

    @Test
    fun `Code blocks with newline padding`() =
        assertAll(
            "CodeBlockTrim2",
            "5: Remove blank line after {.",
            "7: Remove blank line after {.",
            "9: Remove blank line before }.",
            "11: Remove blank line before }.",
        )

    @Test
    fun `Block comment and annotations in members`() = assertAll("CodeBlockTrim3")

    @Test
    fun `Comment in members`() = assertAll("CodeBlockTrim4")
}
