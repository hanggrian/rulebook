package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CodeBlockLineTrimmingCheckTest {
    private val checker = checkerOf<CodeBlockLineTrimmingCheck>()

    @Test
    fun `Rule properties`() = CodeBlockLineTrimmingCheck().assertProperties()

    @Test
    fun `Code blocks without newline padding`() =
        assertEquals(0, checker.read("CodeBlockLineTrimming1"))

    @Test
    fun `Code blocks with newline padding`() =
        assertEquals(4, checker.read("CodeBlockLineTrimming2"))

    @Test
    fun `Block comment and annotations in members`() =
        assertEquals(0, checker.read("CodeBlockLineTrimming3"))

    @Test
    fun `Comment in members`() = assertEquals(0, checker.read("CodeBlockLineTrimming4"))
}
