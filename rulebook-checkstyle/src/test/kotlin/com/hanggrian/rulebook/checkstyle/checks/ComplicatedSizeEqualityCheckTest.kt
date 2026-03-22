package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class ComplicatedSizeEqualityCheckTest : CheckTest() {
    override val check = ComplicatedSizeEqualityCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Size check without operator`() = assertAll("ComplicatedSizeEquality1")

    @Test
    fun `Size check with operator`() =
        assertAll(
            "ComplicatedSizeEquality2",
            "5:17: Replace comparison with 'isEmpty'.",
            "6:24: Replace comparison with '!isEmpty'.",
        )

    @Test
    fun `Target last dot`() =
        assertAll(
            "ComplicatedSizeEquality3",
            "7:21: Replace comparison with 'isEmpty'.",
        )
}
