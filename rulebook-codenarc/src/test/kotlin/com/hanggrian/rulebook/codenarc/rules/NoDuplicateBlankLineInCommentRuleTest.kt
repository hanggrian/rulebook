package com.hanggrian.rulebook.codenarc.rules

import kotlin.test.Test

class NoDuplicateBlankLineInCommentRuleTest : RuleTest<NoDuplicateBlankLineInCommentRule>() {
    override fun createRule() = NoDuplicateBlankLineInCommentRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Single empty line in EOL comment`() =
        assertNoViolations(
            """
            def foo() {
                // Lorem ipsum
                //
                // dolor sit amet.
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiple empty lines in EOL comment`() =
        assertSingleViolation(
            """
            def foo() {
                // Lorem ipsum
                //
                //
                // dolor sit amet.
            }
            """.trimIndent(),
            4,
            "//",
            "Remove consecutive blank line after '//'.",
        )
}
