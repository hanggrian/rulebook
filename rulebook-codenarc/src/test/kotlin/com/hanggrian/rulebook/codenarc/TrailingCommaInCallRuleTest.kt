package com.hanggrian.rulebook.codenarc

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
            "Omit trailing comma.",
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
            "Put trailing comma.",
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

    @Test
    fun `Aware of chained single-line calls`() =
        assertNoViolations(
            """
            def foo() {
                bar(1)
                    .bar(2)
                    .bar(
                        3,
                    )
            }
            """.trimIndent(),
        )
}
