package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class FloatLiteralTaggingCheckTest {
    private val checker = checkerOf<FloatLiteralTaggingCheck>()

    @Test
    fun `Rule properties`() = FloatLiteralTaggingCheck().assertProperties()

    @Test
    fun `Lowercase literal floats`() = assertEquals(0, checker.read("FloatLiteralTagging1"))

    @Test
    fun `Uppercase literal floats`() = assertEquals(2, checker.read("FloatLiteralTagging2"))

    @Test
    fun `Lowercase literal hexadecimals`() = assertEquals(2, checker.read("FloatLiteralTagging3"))

    @Test
    fun `Uppercase literal hexadecimals`() = assertEquals(0, checker.read("FloatLiteralTagging4"))
}
