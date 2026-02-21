package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import com.hanggrian.rulebook.codenarc.violationOf
import kotlin.test.Test
import kotlin.test.assertIs

class BracketsTrimRuleTest : RuleTest<BracketsTrimRule>() {
    override fun createRule() = BracketsTrimRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<BracketsTrimVisitor>(rule.astVisitor)
    }

    @Test
    fun `Brackets without newline padding`() =
        assertNoViolations(
            """
            def foo() {
                bar = [
                    1,
                    2,
                ]
                baz = [
                    '3': 3,
                    '4': 4,
                ]
            }
            """.trimIndent(),
        )

    @Test
    fun `Brackets with newline padding`() =
        assertViolations(
            """
            def foo() {
                bar = [

                    1,
                    2,

                ]
                baz = [

                    '3': 3,
                    '4': 4,

                ]
            }
            """.trimIndent(),
            violationOf(3, "", "Remove blank line after '['."),
            violationOf(6, "", "Remove blank line before ']'."),
            violationOf(9, "", "Remove blank line after '['."),
            violationOf(12, "", "Remove blank line before ']'."),
        )

    @Test
    fun `Comments are considered content`() =
        assertNoViolations(
            """
            def foo() {
                bar = [
                    // Lorem
                    1,
                    2,
                    // ipsum.
                ]
                baz = [
                    // Lorem
                    '3': 3,
                    '4': 4,
                    // ipsum.
                ]
            }
            """.trimIndent(),
        )
}
