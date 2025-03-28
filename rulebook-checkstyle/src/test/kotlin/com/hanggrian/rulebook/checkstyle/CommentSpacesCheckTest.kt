package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CommentSpacesCheckTest {
    private val checker = treeWalkerCheckerOf<CommentSpacesCheck>()

    @Test
    fun `Rule properties`() = CommentSpacesCheck().assertProperties()

    @Test
    fun `With whitespace`() = assertEquals(0, checker.read("CommentSpaces1"))

    @Test
    fun `Without whitespace`() = assertEquals(2, checker.read("CommentSpaces2"))

    @Test
    fun `Ignore block comment`() = assertEquals(0, checker.read("CommentSpaces3"))
}
