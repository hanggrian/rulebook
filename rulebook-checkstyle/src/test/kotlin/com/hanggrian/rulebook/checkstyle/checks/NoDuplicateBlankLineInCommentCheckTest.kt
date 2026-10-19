package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoDuplicateBlankLineInCommentCheckTest : CheckTest() {
    override val check = NoDuplicateBlankLineInCommentCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single empty line in EOL comment`() = assertAll("NoDuplicateBlankLineInComment1")

    @Test
    fun `Multiple empty lines in EOL comment`() =
        assertAll(
            "NoDuplicateBlankLineInComment2",
            "7:9: Remove consecutive blank line after '//'.",
        )
}
