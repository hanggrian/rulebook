package com.hanggrian.rulebook.codenarc.rules

import kotlin.test.Test

class CommentTrimmingRuleTest : RuleTest<CommentTrimmingRule>() {
    override fun createRule() = CommentTrimmingRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Comment without initial and final newline`() =
        assertNoViolations(
            """
            fun foo() {
                // Lorem ipsum.
            }
            """.trimIndent(),
        )

    @Test
    fun `Comment with initial and final newline`() =
        assertTwoViolations(
            """
            fun foo() {
                //
                // Lorem ipsum.
                //
            }
            """.trimIndent(),
            2,
            "//",
            "Remove blank line after '//'.",
            4,
            "//",
            "Remove blank line after '//'.",
        )

    @Test
    fun `Skip blank comment`() =
        assertNoViolations(
            """
            fun foo() {

                //

            }
            """.trimIndent(),
        )

    @Test
    fun `Skip comment with code`() =
        assertNoViolations(
            """
            fun foo() {
                println() //
                println() // Lorem ipsum.
                println() //
            }
            """.trimIndent(),
        )
}
