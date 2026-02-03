package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class CommonFunctionPositionCheckTest : CheckTest() {
    override val check = CommonFunctionPositionCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Special function at the bottom`() = assertAll("CommonFunctionPosition1")

    @Test
    fun `Special function not at the bottom`() =
        assertAll(
            "CommonFunctionPosition2",
            "5:9: Move 'toString' to last.",
            "12:9: Move 'hashCode' to last.",
        )

    @Test
    fun `Grouped overridden functions`() = assertAll("CommonFunctionPosition3")

    @Test
    fun `Skip static members`() = assertAll("CommonFunctionPosition4")
}
