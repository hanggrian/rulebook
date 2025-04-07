package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class RedundantQualifierCheckTest : CheckTest() {
    override val check = RedundantQualifierCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Consistent class qualifiers`() = assertAll("RedundantQualifier1")

    @Test
    fun `Redundant class qualifiers`() =
        assertAll(
            "RedundantQualifier2",
            "6:14: Omit redundant qualifier.",
            "8:29: Omit redundant qualifier.",
            "11:18: Omit redundant qualifier.",
        )

    @Test
    fun `Consistent method qualifiers`() = assertAll("RedundantQualifier3")

    @Test
    fun `Redundant method qualifiers`() =
        assertAll(
            "RedundantQualifier4",
            "6:39: Omit redundant qualifier.",
            "9:25: Omit redundant qualifier.",
        )
}
