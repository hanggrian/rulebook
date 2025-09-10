package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class RedundantElseCheckTest : CheckTest() {
    override val check = RedundantElseCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `No throw or return in if`() = assertAll("RedundantElse1")

    @Test
    fun `Lift else when if has return`() =
        assertAll(
            "RedundantElse2",
            "7:11: Omit redundant else condition.",
            "9:11: Omit redundant else condition.",
        )

    @Test
    fun `Consider if-else without blocks`() =
        assertAll(
            "RedundantElse3",
            "6:9: Omit redundant else condition.",
            "7:9: Omit redundant else condition.",
        )
}
