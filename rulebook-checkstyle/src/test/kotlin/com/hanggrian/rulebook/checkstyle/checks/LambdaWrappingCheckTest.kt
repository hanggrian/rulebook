package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class LambdaWrappingCheckTest : CheckTest() {
    override val check = LambdaWrappingCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single-line lambda`() = assertAll("LambdaWrapping1")

    @Test
    fun `Multiline lambda expression with newline`() = assertAll("LambdaWrapping2")

    @Test
    fun `Multiline lambda expression without newline`() =
        assertAll(
            "LambdaWrapping3",
            "7:50: Put newline after '->'.",
            "11:28: Put newline after '->'.",
        )
}
