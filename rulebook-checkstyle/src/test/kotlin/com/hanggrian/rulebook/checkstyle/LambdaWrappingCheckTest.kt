package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class LambdaWrappingCheckTest {
    private val checker = checkerOf<LambdaWrappingCheck>()

    @Test
    fun `Rule properties`() = LambdaWrappingCheck().assertProperties()

    @Test
    fun `Single-line lambda`() = assertEquals(0, checker.read("LambdaWrapping1"))

    @Test
    fun `Multiline lambda expression with newline`() =
        assertEquals(0, checker.read("LambdaWrapping2"))

    @Test
    fun `Multiline lambda expression without newline`() =
        assertEquals(1, checker.read("LambdaWrapping3"))

    @Test
    fun `Multiline lambda blocked expression without newline`() =
        assertEquals(1, checker.read("LambdaWrapping4"))
}
