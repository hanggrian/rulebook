package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class BlockCommentTrimCheckTest : CheckTest() {
    override val check = BlockCommentTrimCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Block comment without initial and final newline`() = assertAll("BlockCommentTrim1")

    @Test
    fun `Block tag description with final newline`() =
        assertAll(
            "BlockCommentTrim2",
            "4:8: Remove blank line after /**.",
            "7:7: Remove blank line before */.",
        )

    @Test
    fun `Skip single-line block comment`() =
        assertAll("BlockCommentTrim3", "6:7: Remove blank line before */.")

    @Test
    fun `Skip blank block comment`() = assertAll("BlockCommentTrim4")

    @Test
    fun `Skip multiline block tag description`() = assertAll("BlockCommentTrim5")
}
