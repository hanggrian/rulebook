package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.AssignmentWrapRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class AssignmentWrapRuleTest : AbstractRuleTestCase<AssignmentWrapRule>() {
    override fun createRule() = AssignmentWrapRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<AssignmentWrapRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Single-line assignment`() =
        assertNoViolations(
            """
            def foo() {
                var bar = 1 + 2
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline assignment with breaking assignee`() =
        assertNoViolations(
            """
            def foo() {
                var bar =
                    1 +
                        2
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline assignment with non-breaking assignee`() =
        assertSingleViolation(
            """
            def foo() {
                var bar = 1 +
                    2
            }
            """.trimIndent(),
            2,
            "var bar = 1 +",
            Messages[MSG],
        )

    @Test
    fun `Multiline variable but single-line value`() =
        assertNoViolations(
            """
            def foo(var bar) {
                bar
                    .baz = 1
            }

            class Bar {
                var baz
            }
            """.trimIndent(),
        )

    @Test
    fun `Skip collection assignment`() =
        assertNoViolations(
            """
            def foo() {
                var bar = [
                    1,
                    2,
                ]
            }
            """.trimIndent(),
        )
}
