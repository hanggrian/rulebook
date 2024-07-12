package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.SwitchMultipleBranchingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class SwitchMultipleBranchingRuleTest : AbstractRuleTestCase<SwitchMultipleBranchingRule>() {
    override fun createRule() = SwitchMultipleBranchingRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

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
}
