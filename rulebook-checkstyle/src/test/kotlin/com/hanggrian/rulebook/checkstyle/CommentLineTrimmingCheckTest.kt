package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CommentLineTrimmingCheckTest {
    private val checker = checkerOf<CommentLineTrimmingCheck>()

    @Test
    fun `Rule properties`(): Unit = CommentLineTrimmingCheck().assertProperties()

    @Test
    fun `Comment without initial and final newline`() =
        assertEquals(0, checker.read("CommentLineTrimming1"))

    @Test
    fun `Comment with initial and final newline`() =
        assertEquals(2, checker.read("CommentLineTrimming2"))

    @Test
    fun `Skip blank comment`() = assertEquals(0, checker.read("CommentLineTrimming3"))
}
