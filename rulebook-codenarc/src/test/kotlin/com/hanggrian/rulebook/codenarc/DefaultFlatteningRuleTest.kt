package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.DefaultFlatteningRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class DefaultFlatteningRuleTest : AbstractRuleTestCase<DefaultFlatteningRule>() {
    override fun createRule() = DefaultFlatteningRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<DefaultFlatteningRule.Visitor>(rule.astVisitor)
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
            Messages[MSG],
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
