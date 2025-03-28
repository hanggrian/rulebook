package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class LambdaWrapCheckTest {
    private val checker = treeWalkerCheckerOf<LambdaWrapCheck>()

    @Test
    fun `Rule properties`() = LambdaWrapCheck().assertProperties()

    @Test
    fun `Single-line lambda`() = assertEquals(0, checker.read("LambdaWrap1"))

    @Test
    fun `Multiline lambda expression with newline`() = assertEquals(0, checker.read("LambdaWrap2"))

    @Test
    fun `Multiline lambda expression without newline`() =
        assertEquals(1, checker.read("LambdaWrap3"))

    @Test
    fun `Multiline lambda blocked expression without newline`() =
        assertEquals(1, checker.read("LambdaWrap4"))
}
