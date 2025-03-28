package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.TrailingCommaInCallRule.Companion.MSG_MULTI
import com.hanggrian.rulebook.codenarc.TrailingCommaInCallRule.Companion.MSG_SINGLE
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class TrailingCommaInCallRuleTest : AbstractRuleTestCase<TrailingCommaInCallRule>() {
    override fun createRule() = TrailingCommaInCallRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<TrailingCommaInCallRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Single line parameter without trailing comma`() =
        assertNoViolations(
            """
            def foo() {
                bar(1, 2)
            }
            """.trimIndent(),
        )

    @Test
    fun `Single line parameter with trailing comma`() =
        assertSingleViolation(
            """
            def foo() {
                bar(1, 2,)
            }
            """.trimIndent(),
            2,
            "bar(1, 2,)",
            Messages[MSG_SINGLE],
        )

    @Test
    fun `Multiline parameter without trailing comma`() =
        assertSingleViolation(
            """
            def foo() {
                bar(
                    1,
                    2
                )
            }
            """.trimIndent(),
            4,
            "2",
            Messages[MSG_MULTI],
        )

    @Test
    fun `Multiline parameter with trailing comma`() =
        assertNoViolations(
            """
            def foo() {
                bar(
                    1,
                    2,
                )
            }
            """.trimIndent(),
        )

    @Test
    fun `Skip inline comment`() =
        assertNoViolations(
            """
            def foo() {
                bar(
                    1, // 1
                    2, // 2
                )
            }
            """.trimIndent(),
        )
}
