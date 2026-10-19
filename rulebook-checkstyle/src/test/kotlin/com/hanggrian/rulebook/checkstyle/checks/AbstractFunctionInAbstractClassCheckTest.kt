package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class AbstractFunctionInAbstractClassCheckTest : CheckTest() {
    override val check = AbstractFunctionInAbstractClassCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Abstract class with abstract function`() = assertAll("AbstractFunctionInAbstractClass1")

    @Test
    fun `Abstract class without abstract function`() =
        assertAll("AbstractFunctionInAbstractClass2", "4:5: Omit 'abstract' modifier.")

    @Test
    fun `Skip class with inheritance`() = assertAll("AbstractFunctionInAbstractClass3")
}
