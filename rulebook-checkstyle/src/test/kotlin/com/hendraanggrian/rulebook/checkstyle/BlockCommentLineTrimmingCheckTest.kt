package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BlockCommentLineTrimmingCheckTest {
    private val checker = checkerOf<BlockCommentLineTrimmingCheck>()

    @Test
    fun `Rule properties()`(): Unit = BlockCommentLineTrimmingCheck().assertProperties()

    @Test
    fun `Summary without initial and final newline`() =
        assertEquals(0, checker.read("BlockCommentLineTrimming1"))

    @Test
    fun `Summary with initial and final newline`() =
        assertEquals(2, checker.read("BlockCommentLineTrimming2"))

    @Test
    fun `Block tag description with final newline`() =
        assertEquals(1, checker.read("BlockCommentLineTrimming3"))
}
