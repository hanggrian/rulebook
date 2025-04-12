package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class LambdaWrapRuleTest : AbstractRuleTestCase<LambdaWrapRule>() {
    override fun createRule() = LambdaWrapRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<LambdaWrapRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Single-line lambda`() =
        assertNoViolations(
            """
            def foo() {
                Function<Int, String> bar = param -> new StringBuilder().append('').toString()
                baz(param -> { new StringBuilder().append('').toString() })

                Closure<String> bar2 = { param -> new StringBuilder().append('').toString() }
                baz2({ param -> new StringBuilder().append('').toString() })
            }

            void baz(Function<Int, String> function) {}

            void baz2(Closure<String> function) {}
            """.trimIndent(),
        )

    @Test
    fun `Multiline lambda expression with newline`() =
        assertNoViolations(
            """
            def foo() {
                Function<Int, String> bar = param ->
                    new StringBuilder()
                        .append("")
                        .toString()
                baz(
                    param -> {
                        new StringBuilder()
                            .append("")
                            .toString()
                    },
                )

                Closure<String> bar2 = { param ->
                    new StringBuilder()
                        .append("")
                        .toString()
                }
                baz2({ param ->
                    new StringBuilder()
                        .append("")
                        .toString()
                })
            }

            void baz(Function<Int, String> function) {}

            void baz2(Closure<String> function) {}
            """.trimIndent(),
        )

    @Test
    fun `Multiline lambda expression without newline`() =
        assertViolations(
            """
            def foo() {
                Function<Int, String> bar = param -> new StringBuilder()
                    .append("")
                    .toString()
                baz(
                    param -> { new StringBuilder()
                        .append("")
                        .toString()
                    },
                )

                Closure<String> bar2 = { param -> new StringBuilder()
                    .append("")
                    .toString()
                }
                baz2({ param -> new StringBuilder()
                    .append("")
                    .toString()
                })
            }

            void baz(Function<Int, String> function) {}

            void baz2(Closure<String> function) {}
            """.trimIndent(),
            violationOf(
                2,
                "Function<Int, String> bar = param -> new StringBuilder()",
                "Put newline after '->'.",
            ),
            violationOf(
                6,
                "param -> { new StringBuilder()",
                "Put newline after '->'.",
            ),
            violationOf(
                12,
                "Closure<String> bar2 = { param -> new StringBuilder()",
                "Put newline after '->'.",
            ),
            violationOf(
                16,
                "baz2({ param -> new StringBuilder()",
                "Put newline after '->'.",
            ),
        )
}
