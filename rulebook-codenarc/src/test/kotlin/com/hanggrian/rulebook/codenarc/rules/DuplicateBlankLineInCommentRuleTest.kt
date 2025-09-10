package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class DuplicateBlankLineInCommentRuleTest :
    AbstractRuleTestCase<DuplicateBlankLineInCommentRule>() {
    override fun createRule() = DuplicateBlankLineInCommentRule()

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
