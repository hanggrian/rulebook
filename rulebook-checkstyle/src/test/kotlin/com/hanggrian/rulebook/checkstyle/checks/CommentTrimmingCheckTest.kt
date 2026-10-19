package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class CommentTrimmingCheckTest : CheckTest() {
    override val check = CommentTrimmingCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Comment without initial and final newline`() = assertAll("CommentTrimming1")

    @Test
    fun `Comment with initial and final newline`() =
        assertAll(
            "CommentTrimming2",
            "4:5: Remove blank line after '//'.",
            "6:5: Remove blank line after '//'.",
        )

    @Test
    fun `Skip blank comment`() = assertAll("CommentTrimming3")

    @Test
    fun `Skip comment with code`() = assertAll("CommentTrimming4")
}
