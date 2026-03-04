package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import com.hanggrian.rulebook.codenarc.violationOf
import kotlin.test.Test
import kotlin.test.assertIs

class RedundantEqualityRuleTest : RuleTest<RedundantEqualityRule>() {
    override fun createRule() = RedundantEqualityRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<RedundantEqualityVisitor>(rule.astVisitor)
    }

    @Test
    fun `Constant referential equalities`() =
        assertNoViolations(
            """
            def foo(Object foo) {
                if (foo.is(null)) {
                } else if (!foo.is(true)) {
                } else if (foo.is(1.0f)) {
                } else if (!foo.is(0)) {
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Constant structural equalities`() =
        assertViolations(
            """
            def foo(Object foo) {
                if (foo == null) {
                } else if (foo != true) {
                } else if (foo == 1.0f) {
                } else if (foo != 0) {
                }
            }
            """.trimIndent(),
            violationOf(
                2,
                "if (foo == null) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                3,
                "} else if (foo != true) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                4,
                "} else if (foo == 1.0f) {",
                "Replace equality with 'is()'.",
            ),
            violationOf(
                5,
                "} else if (foo != 0) {",
                "Replace equality with 'is()'.",
            ),
        )

    @Test
    fun `Target chained condition`() =
        assertTwoViolations(
            """
            def foo(Object foo) {
                if (foo == null || foo == false) {
                }
            }
            """.trimIndent(),
            2,
            "if (foo == null || foo == false) {",
            "Replace equality with 'is()'.",
            2,
            "if (foo == null || foo == false) {",
            "Replace equality with 'is()'.",
        )
}
