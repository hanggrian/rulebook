package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class AbstractClassDefinitionCheckTest {
    private val checker = treeWalkerCheckerOf<AbstractClassDefinitionCheck>()

    @Test
    fun `Rule properties`() = AbstractClassDefinitionCheck().assertProperties()

    @Test
    fun `Abstract class with abstract function`() =
        assertEquals(0, checker.read("AbstractClassDefinition1"))

    @Test
    fun `Abstract class without abstract function`() =
        assertEquals(1, checker.read("AbstractClassDefinition2"))

    @Test
    fun `Skip class with inheritance`() = assertEquals(0, checker.read("AbstractClassDefinition3"))
}
