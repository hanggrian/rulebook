package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class NumberSuffixForFloatCheckTest {
    private val checker = treeWalkerCheckerOf<NumberSuffixForFloatCheck>()

    @Test
    fun `Rule properties`() = NumberSuffixForFloatCheck().assertProperties()

    @Test
    fun `Lowercase literal floats`() = assertEquals(0, checker.read("NumberSuffixForFloat1"))

    @Test
    fun `Uppercase literal floats`() = assertEquals(2, checker.read("NumberSuffixForFloat2"))
}
