package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoRedundantQualifierCheckTest : CheckTest() {
    override val check = NoRedundantQualifierCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Consistent class qualifiers`() = assertAll("NoRedundantQualifier1")

    @Test
    fun `Redundant class qualifiers`() =
        assertAll(
            "NoRedundantQualifier2",
            "6:14: Omit redundant qualifier.",
            "8:29: Omit redundant qualifier.",
            "11:18: Omit redundant qualifier.",
        )

    @Test
    fun `Consistent method qualifiers`() = assertAll("NoRedundantQualifier3")

    @Test
    fun `Redundant method qualifiers`() =
        assertAll(
            "NoRedundantQualifier4",
            "6:39: Omit redundant qualifier.",
            "9:25: Omit redundant qualifier.",
        )
}
