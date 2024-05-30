package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class EmptyCommentLineJoiningCheckTest {
    private val checker = checkerOf<EmptyCommentLineJoiningCheck>()

    @Test
    fun `Rule properties()`(): Unit = EmptyCommentLineJoiningCheck().assertProperties()

    @Test
    fun `Single empty line in EOL comment`() =
        assertEquals(0, checker.read("EmptyCommentLineJoining1"))

    @Test
    fun `Multiple empty lines in EOL comment`() =
        assertEquals(1, checker.read("EmptyCommentLineJoining2"))
}
