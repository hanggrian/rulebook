package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.DefaultDenestingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class DefaultDenestingRuleTest : AbstractRuleTestCase<DefaultDenestingRule>() {
    override fun createRule() = DefaultDenestingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<DefaultDenestingRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `No throw or return in case`() =
        assertNoViolations(
            """
            void foo(int bar) {
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
            void foo() {
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
            Messages[MSG],
        )

    @Test
    fun `Skip if not all case blocks have return or throw`() =
        assertNoViolations(
            """
            void foo() {
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
