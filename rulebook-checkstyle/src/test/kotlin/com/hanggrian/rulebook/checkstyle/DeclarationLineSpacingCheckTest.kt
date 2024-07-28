package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class DeclarationLineSpacingCheckTest {
    private val checker = checkerOf<DeclarationLineSpacingCheck>()

    @Test
    fun `Rule properties`() = DeclarationLineSpacingCheck().assertProperties()

    @Test
    fun `Declarations with newline`() = assertEquals(0, checker.read("DeclarationLineSpacing1"))

    @Test
    fun `Declarations without newline`() = assertEquals(3, checker.read("DeclarationLineSpacing2"))

    @Test
    fun `Block comment in declarations`() = assertEquals(0, checker.read("DeclarationLineSpacing3"))

    @Test
    fun `Skip fields`() = assertEquals(0, checker.read("DeclarationLineSpacing4"))
}
