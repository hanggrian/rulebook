package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class BracesTrimmingCheckTest : CheckTest() {
    override val check = BracesTrimmingCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Braces without newline padding`() = assertAll("BracesTrimming1")

    @Test
    fun `Braces with newline padding`() =
        assertAll(
            "BracesTrimming2",
            "5: Remove blank line after '{'.",
            "7: Remove blank line after '{'.",
            "9: Remove blank line before '}'.",
            "11: Remove blank line before '}'.",
        )

    @Test
    fun `Comments are considered content`() = assertAll("BracesTrimming3")

    @Test
    fun `Block comment and annotations in members`() = assertAll("BracesTrimming4")
}
