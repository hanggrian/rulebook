package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.SwitchCaseBranchingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class SwitchCaseBranchingRuleTest : AbstractRuleTestCase<SwitchCaseBranchingRule>() {
    override fun createRule() = SwitchCaseBranchingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<SwitchCaseBranchingRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Multiple switch branches`() =
        assertNoViolations(
            """
            void foo() {
                switch (bar) {
                    case 0:
                        baz()
                        break
                    case 1:
                        baz()
                        break
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Single switch branch`() =
        assertSingleViolation(
            """
            void foo() {
                switch (bar) {
                    case 0:
                        baz()
                        break
                }
            }
            """.trimIndent(),
            2,
            "switch (bar) {",
            Messages[MSG],
        )

    @Test
    fun `Skip single branch if it has fall through condition`() =
        assertNoViolations(
            """
            void foo() {
                switch (bar) {
                    case 0:
                    case 1:
                        baz()
                        break
                }
            }
            """.trimIndent(),
        )
}
