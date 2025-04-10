package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class AssignmentWrapRuleTest : AbstractRuleTestCase<AssignmentWrapRule>() {
    override fun createRule() = AssignmentWrapRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<AssignmentWrapRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Single-line assignment`() =
        assertNoViolations(
            """
            def foo() {
                var bar = 1 + 2
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline assignment with breaking assignee`() =
        assertNoViolations(
            """
            def foo() {
                var bar =
                    1 +
                        2
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline assignment with non-breaking assignee`() =
        assertSingleViolation(
            """
            def foo() {
                var bar = 1 +
                    2
            }
            """.trimIndent(),
            2,
            "var bar = 1 +",
            "Break assignment into newline.",
        )

    @Test
    fun `Multiline variable but single-line value`() =
        assertNoViolations(
            """
            def foo(var bar) {
                bar
                    .baz = 1
            }

            class Bar {
                var baz
            }
            """.trimIndent(),
        )

    @Test
    fun `Allow comments after assign operator`() =
        assertNoViolations(
            """
            def foo() {
                var bar =
                    // Comment
                    1 +
                        2;
                var baz = /* Short comment */
                    1 +
                        2;
                var qux =
                    /** Long comment */1 +
                    2;
            }
            """.trimIndent(),
        )

    @Test
    fun `Skip lambda initializers`() =
        assertNoViolations(
            """
            def foo() {
                var bar = (a) -> {
                    println(a)
                }
                var baz = { a ->
                    println(a)
                }

                var bar2 = (a) -> println(a)
                var baz2 = { a -> println(a) }
            }
            """.trimIndent(),
        )

    @Test
    fun `Skip collection initializers`() =
        assertNoViolations(
            """
            def foo() {
                var bar = [
                    1,
                    2,
                ]
                var baz = [
                    a: 1,
                    b: 2,
                ]
            }
            """.trimIndent(),
        )
}
