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
            def foo() {
                if (true) {
                }
            }

            def bar() {
                if (true) {
                    baz()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Invert if with multiline statement or two statements`() =
        assertTwoViolations(
            """
            def foo() {
                if (true) {
                    baz()
                    baz()
                }
            }

            def bar() {
                if (true) {
                    baz(
                        0
                    )
                }
            }
            """.trimIndent(),
            2,
            "if (true) {",
            Messages[MSG_INVERT],
            9,
            "if (true) {",
            Messages[MSG_INVERT],
        )

    @Test
    fun `Lift else when there is no else if`() =
        assertSingleViolation(
            """
            def foo() {
                if (true) {
                    baz()
                } else {
                    baz()
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
            def foo() {
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
    fun `Skip block with jump statement`() =
        assertNoViolations(
            """
            def foo() {
                if (true) {
                    baz()
                    return
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Capture trailing non-ifs`() =
        assertSingleViolation(
            """
            def foo() {
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

    @Test
    fun `Skip recursive if-else because it is not safe to return in inner blocks`() =
        assertNoViolations(
            """
            def foo() {
                if (true) {
                    if (true) {
                        baz()
                        baz()
                    }
                }
                baz()
            }
            """.trimIndent(),
        )
}
