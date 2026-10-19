package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoRedundantElseCheckTest : CheckTest() {
    override val check = NoRedundantElseCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `No throw or return in if`() = assertAll("NoRedundantElse1")

    @Test
    fun `Lift else when if has return`() =
        assertAll(
            "NoRedundantElse2",
            "7:11: Omit redundant 'else' condition.",
            "9:11: Omit redundant 'else' condition.",
        )

    @Test
    fun `Consider if-else without blocks`() =
        assertAll(
            "NoRedundantElse3",
            "6:9: Omit redundant 'else' condition.",
            "7:9: Omit redundant 'else' condition.",
        )
}
