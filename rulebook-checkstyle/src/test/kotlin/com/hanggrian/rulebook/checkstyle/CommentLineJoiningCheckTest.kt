package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CommentLineJoiningCheckTest {
    private val checker = checkerOf<CommentLineJoiningCheck>()

    @Test
    fun `Rule properties`(): Unit = CommentLineJoiningCheck().assertProperties()

    @Test
    fun `Single empty line in EOL comment`() = assertEquals(0, checker.read("CommentLineJoining1"))

    @Test
    fun `Multiple empty lines in EOL comment`() =
        assertEquals(1, checker.read("CommentLineJoining2"))
}
