package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class UnnecessaryAbstractCheckTest : CheckTest() {
    override val check = UnnecessaryAbstractCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Abstract class with abstract function`() = assertAll("UnnecessaryAbstract1")

    @Test
    fun `Abstract class without abstract function`() =
        assertAll("UnnecessaryAbstract2", "4:5: Omit abstract modifier.")

    @Test
    fun `Skip class with inheritance`() = assertAll("UnnecessaryAbstract3")
}
