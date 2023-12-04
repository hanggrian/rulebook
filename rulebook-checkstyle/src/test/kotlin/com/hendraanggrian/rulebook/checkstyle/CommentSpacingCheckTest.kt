package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CommentSpacingCheckTest {
    private val checker = prepareChecker(CommentSpacingCheck::class)

    @Test
    fun `With whitespace`() = assertEquals(0, checker.process(prepareFiles("CommentSpacing1")))

    @Test
    fun `Without whitespace`() = assertEquals(2, checker.process(prepareFiles("CommentSpacing2")))

    @Test
    fun `Ignore block comment`() = assertEquals(0, checker.process(prepareFiles("CommentSpacing3")))
}
