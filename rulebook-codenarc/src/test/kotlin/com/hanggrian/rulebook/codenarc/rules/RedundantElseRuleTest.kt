package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class RedundantElseRuleTest : RuleTest<RedundantElseRule>() {
    override fun createRule() = RedundantElseRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<RedundantElseVisitor>(rule.astVisitor)
    }

    @Test
    fun `No throw or return in if`() =
        assertNoViolations(
            """
            def foo() {
                if (true) {
                    baz()
                } else if (false) {
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Lift else when if has return`() =
        assertTwoViolations(
            """
            def foo() {
                if (true) {
                    throw new Exception()
                } else if (false) {
                    return
                } else {
                    baz()
                }
            }
            """.trimIndent(),
            4,
            "} else if (false) {",
            "Omit redundant 'else' condition.",
            6,
            "} else {",
            "Omit redundant 'else' condition.",
        )

    @Test
    fun `Consider if-else without blocks`() =
        assertTwoViolations(
            """
            def foo() {
                if (true) throw new Exception()
                else if (false) return
                else baz()
            }
            """.trimIndent(),
            3,
            "else if (false) return",
            "Omit redundant 'else' condition.",
            4,
            "else baz()",
            "Omit redundant 'else' condition.",
        )
}
