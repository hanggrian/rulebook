package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class CaseSeparatorRuleTest : AbstractRuleTestCase<CaseSeparatorRule>() {
    override fun createRule() = CaseSeparatorRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<CaseSeparatorRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `No line break after single-line branch and line break after multiline branch`() =
        assertNoViolations(
            """
            def foo(int bar) {
                switch (bar) {
                    case 0:
                        baz()
                    case 1:
                        baz()
                        break

                    default:
                        baz()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Line break after single-line branch`() =
        assertSingleViolation(
            """
            def foo(int bar) {
                switch (bar) {
                    case 0:
                        baz()

                    default:
                        baz()
                }
            }
            """.trimIndent(),
            4,
            "baz()",
            "Remove blank line after single-line branch.",
        )

    @Test
    fun `No line break after multiline branch`() =
        assertSingleViolation(
            """
            def foo(int bar) {
                switch (bar) {
                    case 0:
                        baz()
                        break
                    default:
                        baz()
                        break
                }
            }
            """.trimIndent(),
            5,
            "break",
            "Add blank line after multiline branch.",
        )

    @Test
    fun `Branches with comment are always multiline`() =
        assertViolations(
            """
            def foo(int bar) {
                switch (bar) {
                    // Lorem ipsum.
                    case 0:
                        baz()
                    /* Lorem ipsum. */
                    case 1:
                        baz()
                    /** Lorem ipsum. */
                    case 2:
                        baz()
                    default:
                        baz()
                }
            }
            """.trimIndent(),
            violationOf(5, "baz()", "Add blank line after multiline branch."),
            violationOf(8, "baz()", "Add blank line after multiline branch."),
            violationOf(11, "baz()", "Add blank line after multiline branch."),
        )
}
