package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.StatementIsolationRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class StatementIsolationRuleTest : AbstractRuleTestCase<StatementIsolationRule>() {
    override fun createRule() = StatementIsolationRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

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
