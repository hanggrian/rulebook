package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BlockCommentTrimCheckTest {
    private val checker = treeWalkerCheckerOf<BlockCommentTrimCheck>()

    @Test
    fun `Rule properties`() = BlockCommentTrimCheck().assertProperties()

    @Test
    fun `Block comment without initial and final newline`() =
        assertEquals(0, checker.read("BlockCommentTrim1"))

    @Test
    fun `Block tag description with final newline`() =
        assertEquals(2, checker.read("BlockCommentTrim2"))

    @Test
    fun `Skip single-line block comment`() = assertEquals(1, checker.read("BlockCommentTrim3"))

    @Test
    fun `Skip blank block comment`() = assertEquals(0, checker.read("BlockCommentTrim4"))

    @Test
    fun `Skip multiline block tag description`() =
        assertEquals(0, checker.read("BlockCommentTrim5"))
}
