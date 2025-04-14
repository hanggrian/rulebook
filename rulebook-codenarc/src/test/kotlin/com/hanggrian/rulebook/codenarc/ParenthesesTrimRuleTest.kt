package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class ParenthesesTrimRuleTest : AbstractRuleTestCase<ParenthesesTrimRule>() {
    override fun createRule() = ParenthesesTrimRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<ParenthesesTrimRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Parentheses without newline padding`() =
        assertNoViolations(
            """
            def foo(
                var bar
            ) {
                System.out.println(
                    'baz',
                )
            }
            """.trimIndent(),
        )

    @Test
    fun `Parentheses with newline padding`() =
        assertViolations(
            """
            def foo(

                var bar

            ) {
                System.out.println(

                    'baz',

                )
            }
            """.trimIndent(),
            violationOf(2, "", "Remove blank line after '('."),
            violationOf(4, "", "Remove blank line before ')'."),
            violationOf(7, "", "Remove blank line after '('."),
            violationOf(9, "", "Remove blank line before ')'."),
        )

    @Test
    fun `Comments are considered content`() =
        assertNoViolations(
            """
            def foo(
                // Lorem
                var bar
                // ipsum.
            ) {
                System.out.println(
                    // Lorem
                    'baz',
                    // ipsum.
                )
            }
            """.trimIndent(),
        )
}
