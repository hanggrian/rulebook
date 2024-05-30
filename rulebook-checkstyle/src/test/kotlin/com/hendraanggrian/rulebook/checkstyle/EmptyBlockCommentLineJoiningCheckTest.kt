package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class EmptyBlockCommentLineJoiningCheckTest {
    private val checker = checkerOf<EmptyBlockCommentLineJoiningCheck>()

    @Test
    fun `Rule properties()`(): Unit = EmptyBlockCommentLineJoiningCheck().assertProperties()

    @Test
    fun `Single empty line in block comment`() =
        assertEquals(0, checker.read("EmptyBlockCommentLineJoining1"))

    @Test
    fun `Multiple empty lines in block comment`() =
        assertEquals(1, checker.read("EmptyBlockCommentLineJoining2"))
}
