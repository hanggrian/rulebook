package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NestedIfElseCheckTest : CheckTest() {
    override val check = NestedIfElseCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Empty or one statement in if statement`() = assertAll("NestedIfElse1")

    @Test
    fun `Invert if with multiline statement or two statements`() =
        assertAll(
            "NestedIfElse2",
            "5:9: Invert if condition.",
            "12:9: Invert if condition.",
        )

    @Test
    fun `Lift else when there is no else if`() =
        assertAll("NestedIfElse3", "7:11: Lift else and add return in if block.")

    @Test
    fun `Skip else if`() = assertAll("NestedIfElse4")

    @Test
    fun `Skip block with jump statement`() = assertAll("NestedIfElse5")

    @Test
    fun `Capture trailing non-ifs`() = assertAll("NestedIfElse6", "5:9: Invert if condition.")

    @Test
    fun `Skip recursive if-else because it is not safe to return in inner blocks`() =
        assertAll("NestedIfElse7")
}
