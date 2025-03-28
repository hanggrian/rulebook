package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.DuplicateBlankLineInCommentRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
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
            Messages[MSG],
        )
}
