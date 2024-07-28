package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BlockCommentLineTrimmingCheckTest {
    private val checker = checkerOf<BlockCommentLineTrimmingCheck>()

    @Test
    fun `Rule properties`() = BlockCommentLineTrimmingCheck().assertProperties()

    @Test
    fun `Block comment without initial and final newline`() =
        assertEquals(0, checker.read("BlockCommentLineTrimming1"))

    @Test
    fun `Block tag description with final newline`() =
        assertEquals(2, checker.read("BlockCommentLineTrimming2"))

    @Test
    fun `Skip single-line block comment`() =
        assertEquals(1, checker.read("BlockCommentLineTrimming3"))

    @Test
    fun `Skip blank block comment`() = assertEquals(0, checker.read("BlockCommentLineTrimming4"))

    @Test
    fun `Skip multiline block tag description`() =
        assertEquals(0, checker.read("BlockCommentLineTrimming5"))
}
