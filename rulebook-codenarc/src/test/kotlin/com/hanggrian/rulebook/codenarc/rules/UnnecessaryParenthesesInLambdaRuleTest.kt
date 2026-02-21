package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class UnnecessaryParenthesesInLambdaRuleTest : RuleTest<UnnecessaryParenthesesInLambdaRule>() {
    override fun createRule() = UnnecessaryParenthesesInLambdaRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<UnnecessaryParenthesesInLambdaVisitor>(rule.astVisitor)
    }

    @Test
    fun `Single parameter without parentheses`() =
        assertNoViolations(
            """
            def foo() {
                Arrays
                    .asList(1, 2)
                    .forEach(i -> {})
            }
            """.trimIndent(),
        )

    @Test
    fun `Single parameter with parentheses`() =
        assertSingleViolation(
            """
            def foo() {
                Arrays
                    .asList(1, 2)
                    .forEach((i) -> {})
            }
            """.trimIndent(),
            4,
            ".forEach((i) -> {})",
            "Omit parentheses '()'.",
        )

    @Test
    fun `Multiple parameters`() =
        assertNoViolations(
            """
            def foo() {
                Arrays
                    .asList(1, 2)
                    .forEach((i, j) -> {})
            }
            """.trimIndent(),
        )

    @Test
    fun `Skip explicit type`() =
        assertNoViolations(
            """
            def foo() {
                Arrays
                    .asList(1, 2)
                    .forEach((int i) -> {})
            }
            """.trimIndent(),
        )
}
