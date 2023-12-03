package com.hendraanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class AddSpaceBeforeCommentCheckTest {
    private val checker = prepareChecker(AddSpaceBeforeCommentCheck::class)

    @Test
    fun `With whitespace`() =
        assertEquals(0, checker.process(prepareFiles("AddSpaceBeforeComment1")))

    @Test
    fun `Without whitespace`() =
        assertEquals(2, checker.process(prepareFiles("AddSpaceBeforeComment2")))

    @Test
    fun `Ignore block comment`() =
        assertEquals(0, checker.process(prepareFiles("AddSpaceBeforeComment3")))
}
