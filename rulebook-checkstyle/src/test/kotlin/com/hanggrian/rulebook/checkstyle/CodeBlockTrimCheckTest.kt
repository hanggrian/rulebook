package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CodeBlockTrimCheckTest {
    private val checker = treeWalkerCheckerOf<CodeBlockTrimCheck>()

    @Test
    fun `Rule properties`() = CodeBlockTrimCheck().assertProperties()

    @Test
    fun `Code blocks without newline padding`() = assertEquals(0, checker.read("CodeBlockTrim1"))

    @Test
    fun `Code blocks with newline padding`() = assertEquals(4, checker.read("CodeBlockTrim2"))

    @Test
    fun `Block comment and annotations in members`() =
        assertEquals(0, checker.read("CodeBlockTrim3"))

    @Test
    fun `Comment in members`() = assertEquals(0, checker.read("CodeBlockTrim4"))
}
