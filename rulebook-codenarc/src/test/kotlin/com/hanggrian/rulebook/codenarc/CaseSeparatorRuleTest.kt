package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.CaseSeparatorRule.Companion.MSG_MISSING
import com.hanggrian.rulebook.codenarc.CaseSeparatorRule.Companion.MSG_UNEXPECTED
import com.hanggrian.rulebook.codenarc.internals.Messages
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
            Messages[MSG_UNEXPECTED],
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
            Messages[MSG_MISSING],
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
            violationOf(5, "baz()", Messages[MSG_MISSING]),
            violationOf(8, "baz()", Messages[MSG_MISSING]),
            violationOf(11, "baz()", Messages[MSG_MISSING]),
        )
}
