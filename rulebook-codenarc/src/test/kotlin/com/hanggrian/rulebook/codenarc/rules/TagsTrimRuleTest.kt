package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import com.hanggrian.rulebook.codenarc.violationOf
import kotlin.test.Test
import kotlin.test.assertIs

class TagsTrimRuleTest : RuleTest<TagsTrimRule>() {
    override fun createRule() = TagsTrimRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<TagsTrimVisitor>(rule.astVisitor)
    }

    @Test
    fun `Tags without newline padding`() =
        assertNoViolations(
            """
            <
                T
            > void foo() {
                List<
                    T
                > list = new ArrayList<>()
            }
            """.trimIndent(),
        )

    @Test
    fun `Tags with newline padding`() =
        assertViolations(
            """
            <

                T

            > void foo() {
                List<

                    T

                > list = new ArrayList<>()
            }
            """.trimIndent(),
            violationOf(2, "", "Remove blank line after '<'."),
            violationOf(4, "", "Remove blank line before '>'."),
        )

    @Test
    fun `Comments are considered content`() =
        assertNoViolations(
            """
            <
                // Lorem
                T
                // ipsum.
            > void foo() {
                List<
                    // Lorem
                    T
                    // ipsum.
                > list = new ArrayList<>()
            }
            """.trimIndent(),
        )
}
