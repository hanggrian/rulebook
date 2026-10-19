package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class TagsTrimmingCheckTest : CheckTest() {
    override val check = TagsTrimmingCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Tags without newline padding`() = assertAll("TagsTrimming1")

    @Test
    fun `Tags with newline padding`() =
        assertAll(
            "TagsTrimming2",
            "7: Remove blank line after '<'.",
            "9: Remove blank line before '>'.",
            "12: Remove blank line after '<'.",
            "14: Remove blank line before '>'.",
        )

    @Test
    fun `Comments are considered content`() = assertAll("TagsTrimming3")
}
