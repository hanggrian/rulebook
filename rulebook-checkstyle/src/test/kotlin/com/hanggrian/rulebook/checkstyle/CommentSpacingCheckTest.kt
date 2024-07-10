package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CommentSpacingCheckTest {
    private val checker = checkerOf<CommentSpacingCheck>()

    @Test
    fun `Rule properties`(): Unit = CommentSpacingCheck().assertProperties()

    @Test
    fun `With whitespace`() = assertEquals(0, checker.read("CommentSpacing1"))

    @Test
    fun `Without whitespace`() = assertEquals(2, checker.read("CommentSpacing2"))

    @Test
    fun `Ignore block comment`() = assertEquals(0, checker.read("CommentSpacing3"))
}
