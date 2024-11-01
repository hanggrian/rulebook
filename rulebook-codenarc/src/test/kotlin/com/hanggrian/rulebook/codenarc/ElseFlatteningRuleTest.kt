package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.ElseFlatteningRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class ElseFlatteningRuleTest : AbstractRuleTestCase<ElseFlatteningRule>() {
    override fun createRule() = ElseFlatteningRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<ElseFlatteningRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `No throw or return in if`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                    baz()
                } else if (false) {
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Lift else when if has return`() =
        assertSingleViolation(
            """
            void foo() {
                if (true) {
                    throw new Exception()
                } else if (false) {
                    return
                } else {
                    baz()
                }
            }
            """.trimIndent(),
            6,
            "} else {",
            Messages[MSG],
        )

    @Test
    fun `Skip if not all if blocks have jump statement`() =
        assertNoViolations(
            """
            void foo() {
                if (true) {
                    return
                } else if (false) {
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Consider if-else without blocks`() =
        assertSingleViolation(
            """
            void foo() {
                if (true) throw new Exception()
                else if (false) return
                else baz()
            }
            """.trimIndent(),
            4,
            "else baz()",
            Messages[MSG],
        )
}
