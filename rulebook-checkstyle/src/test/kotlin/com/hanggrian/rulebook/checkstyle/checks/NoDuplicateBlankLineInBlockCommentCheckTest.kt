package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoDuplicateBlankLineInBlockCommentCheckTest : CheckTest() {
    override val check = NoDuplicateBlankLineInBlockCommentCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single empty line in block comment`() = assertAll("NoDuplicateBlankLineInBlockComment1")

    @Test
    fun `Multiple empty lines in block comment`() =
        assertAll(
            "NoDuplicateBlankLineInBlockComment2",
            "7:1: Remove consecutive blank line after '*'.",
        )
}
