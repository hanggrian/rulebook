package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class SimpleSizeComparisonCheckTest : CheckTest() {
    override val check = SimpleSizeComparisonCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Size check without operator`() = assertAll("SimpleSizeComparison1")

    @Test
    fun `Size check with operator`() =
        assertAll(
            "SimpleSizeComparison2",
            "5:17: Replace comparison with 'isEmpty'.",
            "6:24: Replace comparison with '!isEmpty'.",
        )

    @Test
    fun `Target last dot`() =
        assertAll(
            "SimpleSizeComparison3",
            "7:21: Replace comparison with 'isEmpty'.",
        )
}
