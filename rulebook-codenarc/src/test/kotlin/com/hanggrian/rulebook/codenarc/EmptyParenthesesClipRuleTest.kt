package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class EmptyParenthesesClipRuleTest : AbstractRuleTestCase<EmptyParenthesesClipRule>() {
    override fun createRule() = EmptyParenthesesClipRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Wrapped parentheses`() =
        assertNoViolations(
            """
            def foo() {
                bar()
            }
            """.trimIndent(),
        )

    @Test
    fun `Unwrapped parentheses`() =
        assertTwoViolations(
            """
            def foo(
            ) {
                bar(

                )
            }
            """.trimIndent(),
            1,
            "def foo(",
            "Convert into '()'.",
            3,
            "bar(",
            "Convert into '()'.",
        )

    @Test
    fun `Allow parentheses with comment`() =
        assertNoViolations(
            """
            def foo(
                // Lorem ipsum.
            ) {
                bar(
                    // Lorem ipsum.
                )
            }
            """.trimIndent(),
        )
}
