package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class RedundantDefaultRuleTest : RuleTest<RedundantDefaultRule>() {
    override fun createRule() = RedundantDefaultRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<RedundantDefaultVisitor>(rule.astVisitor)
    }

    @Test
    fun `No throw or return in case`() =
        assertNoViolations(
            """
            def foo(var bar) {
                switch (bar) {
                    case 0:
                        baz()
                        break
                    case 1:
                        baz()
                        break
                    default:
                        baz()
                        break
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Lift else when case has return`() =
        assertSingleViolation(
            """
            def foo(var bar) {
                switch (bar) {
                    case 0:
                        throw new Exception()
                    case 1:
                        return
                    default:
                        baz()
                        break
                }
            }
            """.trimIndent(),
            8,
            "baz()",
            "Omit redundant 'default' condition.",
        )

    @Test
    fun `Skip if not all case blocks have jump statement`() =
        assertNoViolations(
            """
            def foo(var bar) {
                switch (bar) {
                    case 0:
                        return
                    case 1:
                        baz()
                        break
                    default:
                        baz()
                        break
                }
            }
            """.trimIndent(),
        )
}
