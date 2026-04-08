package com.hanggrian.rulebook.codenarc.rules

import kotlin.test.Test
import kotlin.test.assertIs

class ComplicatedAssignmentRuleTest : RuleTest<ComplicatedAssignmentRule>() {
    override fun createRule() = ComplicatedAssignmentRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<ComplicatedAssignmentVisitor>(rule.astVisitor)
    }

    @Test
    fun `Shorthand assignments`() =
        assertNoViolations(
            """
            def foo() {
                var bar = 0
                bar += 1
                bar -= 1
                bar *= 1
                bar /= 1
                bar %= 1
            }
            """.trimIndent(),
        )

    @Test
    fun `Complicated assignments`() =
        assertViolations(
            """
            def foo() {
                var bar = 0
                bar = bar + 1
                bar = bar - 1
                bar = bar * 1
                bar = bar / 1
                bar = bar % 1
            }
            """.trimIndent(),
            violationOf(3, "bar = bar + 1", "Use assignment operator '+='."),
            violationOf(4, "bar = bar - 1", "Use assignment operator '-='."),
            violationOf(5, "bar = bar * 1", "Use assignment operator '*='."),
            violationOf(6, "bar = bar / 1", "Use assignment operator '/='."),
            violationOf(7, "bar = bar % 1", "Use assignment operator '%='."),
        )

    @Test
    fun `Target leftmost operator`() =
        assertSingleViolation(
            """
            def foo() {
                var bar = 0
                bar = bar + 1 - 2 * 3 / 4 % 5
            }
            """.trimIndent(),
            3,
            "bar = bar + 1 - 2 * 3 / 4 % 5",
            "Use assignment operator '+='.",
        )
}
