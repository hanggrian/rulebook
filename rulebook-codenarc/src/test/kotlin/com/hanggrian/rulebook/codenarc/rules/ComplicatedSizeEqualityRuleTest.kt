package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class ComplicatedSizeEqualityRuleTest : RuleTest<ComplicatedSizeEqualityRule>() {
    override fun createRule() = ComplicatedSizeEqualityRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<ComplicatedSizeEqualityVisitor>(rule.astVisitor)
    }

    @Test
    fun `Size check without operator`() =
        assertNoViolations(
            """
            def foo(List<Int> foo) {
                if (foo) {
                } else if (!foo) {
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Size check with operator`() =
        assertTwoViolations(
            """
            def foo(List<Int> foo) {
                if (foo.size() == 0) {
                } else if (foo.size() > 0) {
                }
            }
            """.trimIndent(),
            2,
            "if (foo.size() == 0) {",
            "Replace comparison with truthy.",
            3,
            "} else if (foo.size() > 0) {",
            "Replace comparison with truthy.",
        )

    @Test
    fun `Also target isEmpty`() =
        assertSingleViolation(
            """
            def foo(List<Int> foo) {
                if (foo.isEmpty()) {
                }
            }
            """.trimIndent(),
            2,
            "if (foo.isEmpty()) {",
            "Replace comparison with truthy.",
        )
}
