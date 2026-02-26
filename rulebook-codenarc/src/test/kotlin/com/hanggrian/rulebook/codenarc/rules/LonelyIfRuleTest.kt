package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class LonelyIfRuleTest : RuleTest<LonelyIfRule>() {
    override fun createRule() = LonelyIfRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<LonelyIfVisitor>(rule.astVisitor)
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
            5,
            "if (false) {",
            "Replace 'else' with 'else if' condition.",
        )

    @Test
    fun `Capture trailing non-ifs`() =
        assertSingleViolation(
            """
            def foo() {
                if (true) {
                    println()
                } else {
                    if (false) {
                        println()
                    }

                    // Lorem ipsum.
                }
            }
            """.trimIndent(),
            5,
            "if (false) {",
            "Replace 'else' with 'else if' condition.",
        )
}
