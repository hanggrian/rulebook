package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.IfStatementFlatteningRule.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class IfStatementFlatteningRuleTest : AbstractRuleTestCase<IfStatementFlatteningRule>() {
    override fun createRule() = IfStatementFlatteningRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

    @Test
    fun `Empty then statement`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Only 1 line in if statement`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                    bar()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `At least 2 lines in if statement`() =
        assertSingleViolation(
            """
            void foo() {
                if (true) {
                    bar()
                    baz()
                }
            }
            """.trimIndent(),
            2,
            "if (true) {",
            Messages[MSG],
        )

    @Test
    fun `If statement with else`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                    bar()
                    baz()
                } else {
                    bar()
                }
            }
            """.trimIndent(),
        )
}
