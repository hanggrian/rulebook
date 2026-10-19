package com.hanggrian.rulebook.checkstyle.checks

import kotlin.test.Test

class NoParenthesesInLambdaCheckTest : CheckTest() {
    override val check = NoParenthesesInLambdaCheck()

    @Test
    fun `Rule properties`() = check.assertProperties()

    @Test
    fun `Single parameter without parentheses`() = assertAll("NoParenthesesInLambda1")

    @Test
    fun `Single parameter with parentheses`() =
        assertAll("NoParenthesesInLambda2", "9:23: Omit parentheses '()'.")

    @Test
    fun `Multiple parameters`() = assertAll("NoParenthesesInLambda3")

    @Test
    fun `Skip explicit type`() = assertAll("NoParenthesesInLambda4")
}
