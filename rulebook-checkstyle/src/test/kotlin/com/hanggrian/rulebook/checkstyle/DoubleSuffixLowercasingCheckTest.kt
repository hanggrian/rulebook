package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DoubleSuffixLowercasingCheckTest {
    private val checker = checkerOf<DoubleSuffixLowercasingCheck>()

    @Test
    fun `Rule properties`() = DoubleSuffixLowercasingCheck().assertProperties()

    @Test
    fun `Lowercase literal doubles`() = assertEquals(0, checker.read("DoubleSuffixLowercasing1"))

    @Test
    fun `Uppercase literal doubles`() = assertEquals(2, checker.read("DoubleSuffixLowercasing2"))
}
