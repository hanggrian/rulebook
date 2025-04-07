package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class CommentSpacesCheckTest : CheckTest() {
    override val check = CommentSpacesCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `With whitespace`() = assertAll("CommentSpaces1")

    @Test
    fun `Without whitespace`() =
        assertAll(
            "CommentSpaces2",
            "4:5: Put space after //.",
            "5:5: Put space after //.",
        )

    @Test
    fun `Ignore block comment`() = assertAll("CommentSpaces3")

    @Test
    fun `Capture repeated slashes without content`() =
        assertAll(
            "CommentSpaces4",
            "8:5: Put space after //.",
            "10:5: Put space after //.",
        )

    @Test
    fun `Skip special comments`() = assertAll("CommentSpaces5")
}
