package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class NumberSuffixForDoubleCheckTest {
    private val checker = treeWalkerCheckerOf<NumberSuffixForDoubleCheck>()

    @Test
    fun `Rule properties`() = NumberSuffixForDoubleCheck().assertProperties()

    @Test
    fun `Lowercase literal doubles`() = assertEquals(0, checker.read("NumberSuffixForDouble1"))

    @Test
    fun `Uppercase literal doubles`() = assertEquals(2, checker.read("NumberSuffixForDouble2"))
}
