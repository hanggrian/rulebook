package com.hanggrian.rulebook.codenarc.rules

import kotlin.test.Test
import kotlin.test.assertIs

class NoLonelyIfRuleTest : RuleTest<NoLonelyIfRule>() {
    override fun createRule() = NoLonelyIfRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<NoLonelyIfVisitor>(rule.astVisitor)
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
