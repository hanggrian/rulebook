package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.MultilineAssignmentBreakingRule.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class MultilineAssignmentBreakingRuleTest :
    AbstractRuleTestCase<MultilineAssignmentBreakingRule>() {
    override fun createRule() = MultilineAssignmentBreakingRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

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
