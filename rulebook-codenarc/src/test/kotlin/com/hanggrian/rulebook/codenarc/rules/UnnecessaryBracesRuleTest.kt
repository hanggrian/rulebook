package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class UnnecessaryBracesRuleTest : RuleTest<UnnecessaryBracesRule>() {
    override fun createRule() = UnnecessaryBracesRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<UnnecessaryBracesVisitor>(rule.astVisitor)
    }

    @Test
    fun `If not in else block`() =
        assertNoViolations(
            """
            def foo() {
                if (true) {
                    println()
                } else if (false) {
                    println()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `If in else block`() =
        assertSingleViolation(
            """
            def foo() {
                if (true) {
                    println()
                } else {
                    if (false) {
                        println()
                    }
                }
            }
            """.trimIndent(),
            4,
            "} else {",
            "Replace 'else' with 'else if' condition.",
        )
}
