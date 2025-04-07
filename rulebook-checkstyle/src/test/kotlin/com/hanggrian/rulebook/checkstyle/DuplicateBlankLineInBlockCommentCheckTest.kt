package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class DuplicateBlankLineInBlockCommentCheckTest : CheckTest() {
    override val check = DuplicateBlankLineInBlockCommentCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single empty line in block comment`() = assertAll("DuplicateBlankLineInBlockComment1")

    @Test
    fun `Multiple empty lines in block comment`() =
        assertAll(
            "DuplicateBlankLineInBlockComment2",
            "7:1: Remove consecutive blank line after *.",
        )
}
