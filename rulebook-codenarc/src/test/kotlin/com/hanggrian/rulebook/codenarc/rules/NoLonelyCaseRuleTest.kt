package com.hanggrian.rulebook.codenarc.rules

import kotlin.test.Test
import kotlin.test.assertIs

class NoLonelyCaseRuleTest : RuleTest<NoLonelyCaseRule>() {
    override fun createRule() = NoLonelyCaseRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<NoLonelyCaseVisitor>(rule.astVisitor)
    }

    @Test
    fun `Multiple switch branches`() =
        assertNoViolations(
            """
            def foo() {
                switch (bar) {
                    case 0:
                        baz()
                        break
                    case 1:
                        baz()
                        break
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Single switch branch`() =
        assertSingleViolation(
            """
            def foo() {
                switch (bar) {
                    case 0:
                        baz()
                        break
                }
            }
            """.trimIndent(),
            2,
            "switch (bar) {",
            "Replace 'switch' with 'if' condition.",
        )

    @Test
    fun `Skip single branch if it has fall through condition`() =
        assertNoViolations(
            """
            def foo() {
                switch (bar) {
                    case 0:
                    case 1:
                        baz()
                        break
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Target only default case`() =
        assertSingleViolation(
            """
            def foo(var bar) {
                switch (bar) {
                    default:
                        baz()
                        break
                }
            }
            """.trimIndent(),
            2,
            "switch (bar) {",
            "Replace 'switch' with 'if' condition.",
        )
}
