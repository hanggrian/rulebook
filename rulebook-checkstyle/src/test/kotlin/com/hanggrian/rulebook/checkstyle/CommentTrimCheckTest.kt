package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CommentTrimCheckTest {
    private val checker = treeWalkerCheckerOf<CommentTrimCheck>()

    @Test
    fun `Rule properties`() = CommentTrimCheck().assertProperties()

    @Test
    fun `Comment without initial and final newline`() =
        assertEquals(0, checker.read("CommentTrim1"))

    @Test
    fun `Comment with initial and final newline`() = assertEquals(2, checker.read("CommentTrim2"))

    @Test
    fun `Skip blank comment`() = assertEquals(0, checker.read("CommentTrim3"))

    @Test
    fun `Skip comment with code`() = assertEquals(0, checker.read("CommentTrim4"))
}
