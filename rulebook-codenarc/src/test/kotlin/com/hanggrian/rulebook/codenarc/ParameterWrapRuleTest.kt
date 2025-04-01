package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.ParameterWrapRule.Companion.MSG_ARGUMENT
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class ParameterWrapRuleTest : AbstractRuleTestCase<ParameterWrapRule>() {
    override fun createRule() = ParameterWrapRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<ParameterWrapRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Single-line parameters`() =
        assertNoViolations(
            """
            def foo(var a, var b) {}

            void bar() {
                foo(new StringBuilder().toString(), 0)
            }

            def baz() {
                new Foo(new StringBuilder().toString(), 0)
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline parameters each with newline`() =
        assertNoViolations(
            """
            def foo(
                var a,
                var b
            ) {}

            def bar() {
                foo(
                    new StringBuilder()
                        .toString(),
                    0
                )
            }

            def baz() {
                new Foo(
                    new StringBuilder()
                        .toString(),
                    0,
                )
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline parameters each without newline`() =
        assertViolations(
            """
            def foo(
                var a, var b
            ) {}

            def bar() {
                foo(
                    new StringBuilder()
                        .toString(), 0
                )
            }

            def baz() {
                new Foo(
                    new StringBuilder()
                        .toString(), 0
                )
            }
            """.trimIndent(),
            violationOf(8, ".toString(), 0", Messages[MSG_ARGUMENT]),
            violationOf(15, ".toString(), 0", Messages[MSG_ARGUMENT]),
        )

    @Test
    fun `Aware of chained single-line calls`() =
        assertNoViolations(
            """
            def foo() {
                new StringBuilder()
                    .append(1)
                    .append("Hello", 1, 2)
            }
            """.trimIndent(),
        )
}
