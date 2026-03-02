package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class UnnecessaryReturnRuleTest : RuleTest<UnnecessaryReturnRule>() {
    override fun createRule() = UnnecessaryReturnRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<UnnecessaryReturnVisitor>(rule.astVisitor)
    }

    @Test
    fun `Function doesn't end with return`() =
        assertNoViolations(
            """
            def foo() {
                println()
            }
            """.trimIndent(),
        )

    @Test
    fun `Function end with return`() =
        assertSingleViolation(
            """
            def foo() {
                println()
                return
            }
            """.trimIndent(),
            3,
            "return",
            "Last 'return' is not needed.",
        )

    @Test
    fun `Capture trailing non-return`() =
        assertSingleViolation(
            """
            def foo() {
                println()
                return

                // Lorem ipsum.
            }
            """.trimIndent(),
            3,
            "return",
            "Last 'return' is not needed.",
        )

    @Test
    fun `Skip return statement with value`() =
        assertNoViolations(
            """
            def foo() {
                println()
                return true
            }

            String bar() {
                println()
                return null
            }
            """.trimIndent(),
        )
}
