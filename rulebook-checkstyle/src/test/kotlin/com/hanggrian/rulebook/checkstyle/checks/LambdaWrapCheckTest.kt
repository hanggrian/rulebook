package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class LambdaWrapCheckTest : CheckTest() {
    override val check = LambdaWrapCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single-line lambda`() = assertAll("LambdaWrap1")

    @Test
    fun `Multiline lambda expression with newline`() = assertAll("LambdaWrap2")

    @Test
    fun `Multiline lambda expression without newline`() =
        assertAll(
            "LambdaWrap3",
            "7:50: Put newline after '->'.",
            "11:28: Put newline after '->'.",
        )
}
