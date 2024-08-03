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
            Messages.get(MSG, ';'),
            2,
            "int bar = 1; int baz = 2",
            Messages.get(MSG, ';'),
        )

    @Test
    fun `Single statement in block`() =
        assertViolations(
            """
            void foo() {
                if (baz) { println(it) }
                for (baz in [1, 2, 3]) { println(it) }
                while (true) { println(it) }
                do { println(it) } while (true)
            }
            """.trimIndent(),
            violationOf(2, "if (baz) { println(it) }", Messages.get(MSG, '{')),
            violationOf(3, "for (baz in [1, 2, 3]) { println(it) }", Messages.get(MSG, '{')),
            violationOf(4, "while (true) { println(it) }", Messages.get(MSG, '{')),
            violationOf(5, "do { println(it) } while (true)", Messages.get(MSG, '{')),
        )
}
