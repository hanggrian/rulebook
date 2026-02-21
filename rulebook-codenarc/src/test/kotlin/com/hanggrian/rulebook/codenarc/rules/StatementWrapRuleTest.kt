package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import com.hanggrian.rulebook.codenarc.violationOf
import kotlin.test.Test
import kotlin.test.assertIs

class StatementWrapRuleTest : RuleTest<StatementWrapRule>() {
    override fun createRule() = StatementWrapRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<StatementWrapVisitor>(rule.astVisitor)
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
            "Put newline after ';'.",
            2,
            "var bar = 1; var baz = 2",
            "Put newline after ';'.",
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
            violationOf(2, "if (baz) { println(it) }", "Put newline after '{'."),
            violationOf(3, "for (baz in [1, 2, 3]) { println(it) }", "Put newline after '{'."),
            violationOf(4, "while (true) { println(it) }", "Put newline after '{'."),
            violationOf(5, "do { println(it) } while (true)", "Put newline after '{'."),
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
    fun `Skip semicolons found in for command and string`() =
        assertNoViolations(
            """
            def foo() {
                for (int bar = 0; bar < 10; bar++) {
                    var baz = "${'$'}b;ar"
                }
            }
            """.trimIndent(),
        )
}
