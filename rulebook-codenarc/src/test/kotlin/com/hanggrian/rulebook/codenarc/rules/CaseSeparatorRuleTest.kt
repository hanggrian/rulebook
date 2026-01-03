package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import com.hanggrian.rulebook.codenarc.violationOf
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class CaseSeparatorRuleTest : AbstractRuleTestCase<CaseSeparatorRule>() {
    override fun createRule() = CaseSeparatorRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<CaseSeparatorVisitor>(rule.astVisitor)
    }

    @Test
    fun `Single-line branches without line break`() =
        assertNoViolations(
            """
            def foo(int bar) {
                switch (bar) {
                    case 0: baz()
                    case 1: baz(); break
                    default: baz()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline branches with line break`() =
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
    fun `Single-line branches with line break`() =
        assertTwoViolations(
            """
            def foo(int bar) {
                switch (bar) {
                    case 0: baz()

                    case 1: baz(); break

                    default: baz()
                }
            }
            """.trimIndent(),
            3,
            "case 0: baz()",
            "Remove blank line after single-line branch.",
            5,
            "case 1: baz(); break",
            "Remove blank line after single-line branch.",
        )

    @Test
    fun `Multiline branches without line break`() =
        assertTwoViolations(
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
            4,
            "baz()",
            "Add blank line after multiline branch.",
            7,
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

    @Test
    fun `Special Groovy expression`() =
        assertTwoViolations(
            """
            def foo(int bar) {
                def result =
                    switch (bar) {
                        case 0 -> 0

                        case 1 -> 1

                        default -> 2
                    }
            }
            """.trimIndent(),
            4,
            "case 0 -> 0",
            "Remove blank line after single-line branch.",
            6,
            "case 1 -> 1",
            "Remove blank line after single-line branch.",
        )
}
