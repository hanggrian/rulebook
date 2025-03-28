package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DuplicateBlankLineInCommentCheckTest {
    private val checker = treeWalkerCheckerOf<DuplicateBlankLineInCommentCheck>()

    @Test
    fun `Rule properties`() = DuplicateBlankLineInCommentCheck().assertProperties()

    @Test
    fun `Single empty line in EOL comment`() =
        assertEquals(0, checker.read("DuplicateBlankLineInComment1"))

    @Test
    fun `Multiple empty lines in EOL comment`() =
        assertEquals(1, checker.read("DuplicateBlankLineInComment2"))
}
