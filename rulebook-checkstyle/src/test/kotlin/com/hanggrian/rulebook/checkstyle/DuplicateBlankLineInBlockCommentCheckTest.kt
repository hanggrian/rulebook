package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DuplicateBlankLineInBlockCommentCheckTest {
    private val checker = treeWalkerCheckerOf<DuplicateBlankLineInBlockCommentCheck>()

    @Test
    fun `Rule properties`() = DuplicateBlankLineInBlockCommentCheck().assertProperties()

    @Test
    fun `Single empty line in block comment`() =
        assertEquals(0, checker.read("DuplicateBlankLineInBlockComment1"))

    @Test
    fun `Multiple empty lines in block comment`() =
        assertEquals(1, checker.read("DuplicateBlankLineInBlockComment2"))
}
