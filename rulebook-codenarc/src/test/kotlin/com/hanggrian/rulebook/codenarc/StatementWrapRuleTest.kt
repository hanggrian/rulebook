package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.StatementWrapRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class StatementWrapRuleTest : AbstractRuleTestCase<StatementWrapRule>() {
    override fun createRule() = StatementWrapRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<StatementWrapRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Single statement`() =
        assertNoViolations(
            """
            def foo() {
                var bar = 1
                var baz = 2
            }
            """.trimIndent(),
        )

    @Test
    fun `Joined statements`() =
        assertTwoViolations(
            """
            def foo() {
                var bar = 1; var baz = 2
            }
            """.trimIndent(),
            2,
            "var bar = 1; var baz = 2",
            Messages.get(MSG, ';'),
            2,
            "var bar = 1; var baz = 2",
            Messages.get(MSG, ';'),
        )

    @Test
    fun `Single statement in block`() =
        assertViolations(
            """
            def foo() {
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

    @Test
    fun `Distinguish between code and inline comment`() =
        assertNoViolations(
            """
            def foo() {
                var bar = 1 // ;
            }
            """.trimIndent(),
        )

    @Test
    fun `Skip semicolons found in for command`() =
        assertNoViolations(
            """
            def foo() {
                for (int bar = 0; bar < 10; bar++) {
                    int baz = bar
                }
            }
            """.trimIndent(),
        )
}
