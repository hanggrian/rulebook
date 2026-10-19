package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class BlockCommentTrimmingCheckTest : CheckTest() {
    override val check = BlockCommentTrimmingCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Block comment without initial and final newline`() = assertAll("BlockCommentTrimming1")

    @Test
    fun `Block tag description with final newline`() =
        assertAll(
            "BlockCommentTrimming2",
            "4:8: Remove blank line after '/**'.",
            "7:7: Remove blank line before '*/'.",
        )

    @Test
    fun `Skip single-line block comment`() =
        assertAll("BlockCommentTrimming3", "6:7: Remove blank line before '*/'.")

    @Test
    fun `Skip blank block comment`() = assertAll("BlockCommentTrimming4")

    @Test
    fun `Skip multiline block tag description`() = assertAll("BlockCommentTrimming5")
}
