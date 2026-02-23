package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class CommentTrimCheckTest : CheckTest() {
    override val check = CommentTrimCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Comment without initial and final newline`() = assertAll("CommentTrim1")

    @Test
    fun `Comment with initial and final newline`() =
        assertAll(
            "CommentTrim2",
            "4:5: Remove blank line after '//'.",
            "6:5: Remove blank line after '//'.",
        )

    @Test
    fun `Skip blank comment`() = assertAll("CommentTrim3")

    @Test
    fun `Skip comment with code`() = assertAll("CommentTrim4")
}
