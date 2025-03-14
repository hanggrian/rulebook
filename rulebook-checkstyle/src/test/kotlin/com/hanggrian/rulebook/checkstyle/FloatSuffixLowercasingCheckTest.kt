package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class FloatSuffixLowercasingCheckTest {
    private val checker = checkerOf<FloatSuffixLowercasingCheck>()

    @Test
    fun `Rule properties`() = FloatSuffixLowercasingCheck().assertProperties()

    @Test
    fun `Lowercase literal floats`() = assertEquals(0, checker.read("FloatSuffixLowercasing1"))

    @Test
    fun `Uppercase literal floats`() = assertEquals(2, checker.read("FloatSuffixLowercasing2"))
}
