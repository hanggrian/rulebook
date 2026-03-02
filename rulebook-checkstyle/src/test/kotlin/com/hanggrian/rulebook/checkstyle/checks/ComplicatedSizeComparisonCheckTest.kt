package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class ComplicatedSizeComparisonCheckTest : CheckTest() {
    override val check = ComplicatedSizeComparisonCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Size check without operator`() = assertAll("ComplicatedSizeComparison1")

    @Test
    fun `Size check with operator`() =
        assertAll(
            "ComplicatedSizeComparison2",
            "5:17: Replace comparison with 'isEmpty'.",
            "6:24: Replace comparison with '!isEmpty'.",
        )
}
