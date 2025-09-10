package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import com.hanggrian.rulebook.codenarc.violationOf
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class OperatorWrapRuleTest : AbstractRuleTestCase<OperatorWrapRule>() {
    override fun createRule() = OperatorWrapRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<OperatorWrapRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Operators in single-line statement`() =
        assertNoViolations(
            """
            def foo() {
                var bar = 1 * 2
                println(3 + 4 - 5)
            }
            """.trimIndent(),
        )

    @Test
    fun `NL-wrapped operators in multi-line statement`() =
        assertViolations(
            """
            def foo() {
                var bar =
                    1
                        * 2
                println(
                    3
                        + 4
                        - 5,
                )
            }
            """.trimIndent(),
            violationOf(4, "* 2", "Omit newline before operator '*'."),
            violationOf(7, "+ 4", "Omit newline before operator '+'."),
            violationOf(8, "- 5", "Omit newline before operator '-'."),
        )

    @Test
    fun `EOL-wrapped operators in multi-line statement`() =
        assertNoViolations(
            """
            def foo() {
                var bar =
                    1 *
                        2
                println(
                    3 +
                        4 -
                        5,
                )
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline operand need to be wrapped`() =
        assertViolations(
            """
            def foo() {
                var bar =
                    1 * Math.min(
                        2,
                        3,
                    )
                println(
                    4 + Math.min(
                        5,
                        6
                    ) - Math.max(
                        7,
                        8,
                    )
                )
            }
            """.trimIndent(),
            violationOf(3, "1 * Math.min(", "Put newline after operator '*'."),
            violationOf(8, "4 + Math.min(", "Put newline after operator '+'."),
            violationOf(11, ") - Math.max(", "Put newline after operator '-'."),
        )

    @Test
    fun `Allow comments after operator`() =
        assertNoViolations(
            """
            def foo() {
                var bar =
                    1 * // Comment
                        2
                println(
                    3 + /* Short comment */
                        4 -
                        /** Long comment */ 5
                )
            }
            """.trimIndent(),
        )

    @Test
    fun `Skip collection initializers`() =
        assertNoViolations(
            """
            def foo() {
                var bar =
                    1 + [
                        2,
                        3,
                    ] + {
                        4
                    }
            }
            """.trimIndent(),
        )
}
