package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test

class ParenthesesClipRuleTest : RuleTest<ParenthesesClipRule>() {
    override fun createRule() = ParenthesesClipRule()

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
