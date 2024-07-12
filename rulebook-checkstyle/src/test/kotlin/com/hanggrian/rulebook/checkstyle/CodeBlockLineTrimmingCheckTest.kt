package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class CodeBlockLineTrimmingCheckTest {
    private val checker = checkerOf<CodeBlockLineTrimmingCheck>()

    @Test
    fun `Rule properties`() = CodeBlockLineTrimmingCheck().assertProperties()

    @Test
    fun `Class block without first and last newline`() =
        assertEquals(0, checker.read("CodeBlockLineTrimming1"))

    @Test
    fun `Class block with first and last newline`() =
        assertEquals(2, checker.read("CodeBlockLineTrimming2"))

    @Test
    fun `Function block without first and last newline`() =
        assertEquals(0, checker.read("CodeBlockLineTrimming3"))

    @Test
    fun `Function block with first and last newline`() =
        assertEquals(2, checker.read("CodeBlockLineTrimming4"))
}
