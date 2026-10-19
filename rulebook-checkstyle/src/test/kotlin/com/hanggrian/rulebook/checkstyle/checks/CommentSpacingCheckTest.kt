package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class CommentSpacingCheckTest : CheckTest() {
    override val check = CommentSpacingCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `With whitespace`() = assertAll("CommentSpacing1")

    @Test
    fun `Without whitespace`() =
        assertAll(
            "CommentSpacing2",
            "4:5: Put one space after '//'.",
            "5:5: Put one space after '//'.",
        )

    @Test
    fun `Ignore block comment`() = assertAll("CommentSpacing3")

    @Test
    fun `Capture repeated slashes without content`() =
        assertAll(
            "CommentSpacing4",
            "8:5: Put one space after '//'.",
            "10:5: Put one space after '//'.",
        )

    @Test
    fun `Skip special comments`() = assertAll("CommentSpacing5")

    @Test
    fun `Skip comment in comments`() = assertAll("CommentSpacing6")
}
