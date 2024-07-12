package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class BlockCommentLineJoiningCheckTest {
    private val checker = checkerOf<BlockCommentLineJoiningCheck>()

    @Test
    fun `Rule properties`() = BlockCommentLineJoiningCheck().assertProperties()

    @Test
    fun `Single empty line in block comment`() =
        assertEquals(0, checker.read("BlockCommentLineJoining1"))

    @Test
    fun `Multiple empty lines in block comment`() =
        assertEquals(1, checker.read("BlockCommentLineJoining2"))
}
