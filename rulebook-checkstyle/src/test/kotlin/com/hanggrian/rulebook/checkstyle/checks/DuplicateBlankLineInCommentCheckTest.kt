package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class DuplicateBlankLineInCommentCheckTest : CheckTest() {
    override val check = DuplicateBlankLineInCommentCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single empty line in EOL comment`() = assertAll("DuplicateBlankLineInComment1")

    @Test
    fun `Multiple empty lines in EOL comment`() =
        assertAll("DuplicateBlankLineInComment2", "7:9: Remove consecutive blank line after '//'.")
}
