package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class BuiltInFunctionPositionCheckTest : CheckTest() {
    override val check = BuiltInFunctionPositionCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Special function at the bottom`() = assertAll("BuiltInFunctionPosition1")

    @Test
    fun `Special function not at the bottom`() =
        assertAll(
            "BuiltInFunctionPosition2",
            "5:9: Move 'toString' to last.",
            "12:9: Move 'hashCode' to last.",
        )

    @Test
    fun `Grouped overridden functions`() = assertAll("BuiltInFunctionPosition3")

    @Test
    fun `Skip static members`() = assertAll("BuiltInFunctionPosition4")
}
