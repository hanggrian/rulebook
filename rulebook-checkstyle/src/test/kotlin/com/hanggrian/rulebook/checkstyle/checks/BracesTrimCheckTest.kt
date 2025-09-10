package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class BracesTrimCheckTest : CheckTest() {
    override val check = BracesTrimCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Braces without newline padding`() = assertAll("BracesTrim1")

    @Test
    fun `Braces with newline padding`() =
        assertAll(
            "BracesTrim2",
            "5: Remove blank line after {.",
            "7: Remove blank line after {.",
            "9: Remove blank line before }.",
            "11: Remove blank line before }.",
        )

    @Test
    fun `Comments are considered content`() = assertAll("BracesTrim3")

    @Test
    fun `Block comment and annotations in members`() = assertAll("BracesTrim4")
}
