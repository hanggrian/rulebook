package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class OverloadFunctionPositionRuleTest : AbstractRuleTestCase<OverloadFunctionPositionRule>() {
    override fun createRule() = OverloadFunctionPositionRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<OverloadFunctionPositionVisitor>(rule.astVisitor)
    }

    @Test
    fun `Overload function next to each other`() =
        assertNoViolations(
            """
            class Foo {
                def bar(int a) {}

                def bar(String a) {}

                def baz() {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Overload function not next to each other`() =
        assertSingleViolation(
            """
            class Foo {
                def bar(int a) {}

                def baz() {}

                def bar(String a) {}
            }
            """.trimIndent(),
            6,
            "def bar(String a) {}",
            "Move 'bar' next to each other.",
        )

    @Test
    fun `Overload function not next to each other in root`() =
        assertSingleViolation(
            """
            def bar(int a) {}

            def baz() {}

            def bar(String a) {}
            """.trimIndent(),
            5,
            "def bar(String a) {}",
            "Move 'bar' next to each other.",
        )
}
