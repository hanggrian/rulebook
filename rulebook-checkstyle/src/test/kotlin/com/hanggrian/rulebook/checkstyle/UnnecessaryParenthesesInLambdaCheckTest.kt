package com.hanggrian.rulebook.checkstyle

import kotlin.test.Test
import kotlin.test.assertEquals

class UnnecessaryParenthesesInLambdaCheckTest {
    private val checker = treeWalkerCheckerOf<UnnecessaryParenthesesInLambdaCheck>()

    @Test
    fun `Rule properties`() = UnnecessaryParenthesesInLambdaCheck().assertProperties()

    @Test
    fun `Single parameter without parentheses`() =
        assertEquals(0, checker.read("UnnecessaryParenthesesInLambda1"))

    @Test
    fun `Single parameter with parentheses`() =
        assertEquals(1, checker.read("UnnecessaryParenthesesInLambda2"))

    @Test
    fun `Multiple parameters`() = assertEquals(0, checker.read("UnnecessaryParenthesesInLambda3"))

    @Test
    fun `Skip explicit type`() = assertEquals(0, checker.read("UnnecessaryParenthesesInLambda4"))
}
