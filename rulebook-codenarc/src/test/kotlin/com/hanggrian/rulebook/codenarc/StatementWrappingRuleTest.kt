package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.StatementWrappingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class StatementWrappingRuleTest : AbstractRuleTestCase<StatementWrappingRule>() {
    override fun createRule() = StatementWrappingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<StatementWrappingRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Single statement`() =
        assertNoViolations(
            """
            void foo() {
                int bar = 1
                int baz = 2
            }
            """.trimIndent(),
        )

    @Test
    fun `Joined statements`() =
        assertTwoViolations(
            """
            void foo() {
                int bar = 1; int baz = 2
            }
            """.trimIndent(),
            2,
            "int bar = 1; int baz = 2",
            Messages[MSG],
            2,
            "int bar = 1; int baz = 2",
            Messages[MSG],
        )
}
