package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class AbstractClassDefinitionCheckTest : CheckTest() {
    override val check = AbstractClassDefinitionCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Abstract class with abstract function`() = assertAll("AbstractClassDefinition1")

    @Test
    fun `Abstract class without abstract function`() =
        assertAll("AbstractClassDefinition2", "4:5: Omit abstract modifier.")

    @Test
    fun `Skip class with inheritance`() = assertAll("AbstractClassDefinition3")
}
