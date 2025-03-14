package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class AbstractClassFunctionAbstractionCheckTest {
    private val checker = checkerOf<AbstractClassFunctionAbstractionCheck>()

    @Test
    fun `Rule properties`() = AbstractClassFunctionAbstractionCheck().assertProperties()

    @Test
    fun `Abstract class with abstract function`() =
        assertEquals(0, checker.read("AbstractClassFunctionAbstraction1"))

    @Test
    fun `Abstract class without abstract function`() =
        assertEquals(1, checker.read("AbstractClassFunctionAbstraction2"))

    @Test
    fun `Skip class with inheritance`() =
        assertEquals(0, checker.read("AbstractClassFunctionAbstraction3"))
}
