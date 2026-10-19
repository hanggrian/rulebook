package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoNestedIfElseCheckTest : CheckTest() {
    override val check = NoNestedIfElseCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Empty or one statement in if statement`() = assertAll("NoNestedIfElse1")

    @Test
    fun `Invert if with multiline statement or two statements`() =
        assertAll(
            "NoNestedIfElse2",
            "5:9: Invert 'if' condition.",
            "12:9: Invert 'if' condition.",
        )

    @Test
    fun `Lift else when there is no else if`() =
        assertAll("NoNestedIfElse3", "7:11: Lift 'else' and add 'return' in 'if' block.")

    @Test
    fun `Skip else if`() = assertAll("NoNestedIfElse4")

    @Test
    fun `Skip block with jump statement`() = assertAll("NoNestedIfElse5")

    @Test
    fun `Capture trailing non-ifs`() = assertAll("NoNestedIfElse6", "5:9: Invert 'if' condition.")

    @Test
    fun `Skip recursive if-else because it is not safe to return in inner blocks`() =
        assertAll("NoNestedIfElse7")
}
