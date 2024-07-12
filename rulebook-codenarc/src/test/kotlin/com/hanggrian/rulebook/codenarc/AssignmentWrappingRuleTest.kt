package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.AssignmentWrappingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class AssignmentWrappingRuleTest : AbstractRuleTestCase<AssignmentWrappingRule>() {
    override fun createRule() = AssignmentWrappingRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Single-line assignment`() =
        assertNoViolations(
            """
            void foo() {
                int bar = 1 + 2
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline assignment with breaking assignee`() =
        assertNoViolations(
            """
            void foo() {
                int bar =
                    1 +
                        2
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline assignment with non-breaking assignee`() =
        assertSingleViolation(
            """
            void foo() {
                int bar = 1 +
                    2
            }
            """.trimIndent(),
            2,
            "int bar = 1 +",
            Messages[MSG],
        )
}
