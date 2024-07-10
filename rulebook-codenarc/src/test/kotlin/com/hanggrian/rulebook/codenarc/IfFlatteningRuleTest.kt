package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.IfFlatteningRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class IfFlatteningRuleTest : AbstractRuleTestCase<IfFlatteningRule>() {
    override fun createRule() = IfFlatteningRule()

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
