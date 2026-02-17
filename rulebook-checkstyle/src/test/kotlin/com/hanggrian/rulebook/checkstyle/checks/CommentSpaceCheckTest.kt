package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class CommentSpaceCheckTest : CheckTest() {
    override val check = CommentSpaceCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `With whitespace`() = assertAll("CommentSpace1")

    @Test
    fun `Without whitespace`() =
        assertAll(
            "CommentSpace2",
            "4:5: Put one space after //.",
            "5:5: Put one space after //.",
        )

    @Test
    fun `Ignore block comment`() = assertAll("CommentSpace3")

    @Test
    fun `Capture repeated slashes without content`() =
        assertAll(
            "CommentSpace4",
            "8:5: Put one space after //.",
            "10:5: Put one space after //.",
        )

    @Test
    fun `Skip special comments`() = assertAll("CommentSpace5")

    @Test
    fun `Skip comment in comments`() = assertAll("CommentSpace6")
}
