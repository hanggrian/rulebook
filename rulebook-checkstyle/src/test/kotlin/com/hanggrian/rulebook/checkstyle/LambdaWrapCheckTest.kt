package com.hanggrian.rulebook.checkstyle

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
        assertAll("LambdaWrap3", "8:26: Put newline after ->.")

    @Test
    fun `Multiline lambda blocked expression without newline`() =
        assertAll("LambdaWrap4", "10:21: Put newline after ->.")
}
