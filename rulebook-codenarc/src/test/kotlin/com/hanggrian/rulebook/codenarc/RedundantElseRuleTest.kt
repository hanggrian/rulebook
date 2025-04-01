package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.RedundantElseRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class RedundantElseRuleTest : AbstractRuleTestCase<RedundantElseRule>() {
    override fun createRule() = RedundantElseRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<RedundantElseRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `No throw or return in if`() =
        assertNoViolations(
            """
            def foo() {
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
        assertTwoViolations(
            """
            def foo() {
                if (true) {
                    throw new Exception()
                } else if (false) {
                    return
                } else {
                    baz()
                }
            }
            """.trimIndent(),
            4,
            "} else if (false) {",
            Messages[MSG],
            6,
            "} else {",
            Messages[MSG],
        )

    @Test
    fun `Consider if-else without blocks`() =
        assertTwoViolations(
            """
            def foo() {
                if (true) throw new Exception()
                else if (false) return
                else baz()
            }
            """.trimIndent(),
            3,
            "else if (false) return",
            Messages[MSG],
            4,
            "else baz()",
            Messages[MSG],
        )
}
