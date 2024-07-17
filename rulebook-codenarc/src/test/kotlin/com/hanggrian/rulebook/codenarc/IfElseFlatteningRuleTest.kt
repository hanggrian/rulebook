package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.IfElseFlatteningRule.Companion.MSG_INVERT
import com.hanggrian.rulebook.codenarc.IfElseFlatteningRule.Companion.MSG_LIFT
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class IfElseFlatteningRuleTest : AbstractRuleTestCase<IfElseFlatteningRule>() {
    override fun createRule() = IfElseFlatteningRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<IfElseFlatteningRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Empty or one statement in if statement`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                }
            }

            void bar() {
                if (true) {
                    baz()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Invert if with two statements`() =
        assertSingleViolation(
            """
            void foo() {
                if (true) {
                    baz()
                    baz()
                }
            }
            """.trimIndent(),
            2,
            "if (true) {",
            Messages[MSG_INVERT],
        )

    @Test
    fun `Lift else when there is no else if`() =
        assertSingleViolation(
            """
            void foo() {
                if (true) {
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
            4,
            "} else {",
            Messages[MSG_LIFT],
        )

    @Test
    fun `Skip else if`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                    baz()
                    baz()
                } else if (false) {
                    baz()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Capture trailing non-ifs`() =
        assertSingleViolation(
            """
            void foo() {
                if (true) {
                    baz()
                    baz()
                }

                // Lorem ipsum.
            }
            """.trimIndent(),
            2,
            "if (true) {",
            Messages[MSG_INVERT],
        )
}
