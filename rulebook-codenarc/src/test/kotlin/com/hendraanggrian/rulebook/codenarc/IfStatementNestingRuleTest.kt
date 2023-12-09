package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.IfStatementNestingVisitor.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class IfStatementNestingRuleTest : AbstractRuleTestCase<IfStatementNestingRule>() {
    override fun createRule() = IfStatementNestingRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("IfStatementNesting", rule.name)
    }

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
    fun `Inverted if statement`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                    return
                }
                bar()
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
                } else if (false) {
                    bar()
                    baz()
                } else {
                    bar()
                    baz()
                }
            }
            """.trimIndent(),
        )
}
