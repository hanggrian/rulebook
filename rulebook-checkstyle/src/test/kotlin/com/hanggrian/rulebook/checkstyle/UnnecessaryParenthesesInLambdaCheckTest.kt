package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test

class UnnecessaryParenthesesInLambdaCheckTest : CheckTest() {
    override val check = UnnecessaryParenthesesInLambdaCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single parameter without parentheses`() = assertAll("UnnecessaryParenthesesInLambda1")

    @Test
    fun `Single parameter with parentheses`() =
        assertAll("UnnecessaryParenthesesInLambda2", "9:23: Omit parentheses ().")

    @Test
    fun `Multiple parameters`() = assertAll("UnnecessaryParenthesesInLambda3")

    @Test
    fun `Skip explicit type`() = assertAll("UnnecessaryParenthesesInLambda4")
}
