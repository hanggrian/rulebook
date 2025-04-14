package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class TagsTrimCheckTest : CheckTest() {
    override val check = TagsTrimCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Tags without newline padding`() = assertAll("TagsTrim1")

    @Test
    fun `Tags with newline padding`() =
        assertAll(
            "TagsTrim2",
            "7: Remove blank line after <.",
            "9: Remove blank line before >.",
            "12: Remove blank line after <.",
            "14: Remove blank line before >.",
        )

    @Test
    fun `Comments are considered content`() = assertAll("TagsTrim3")
}
