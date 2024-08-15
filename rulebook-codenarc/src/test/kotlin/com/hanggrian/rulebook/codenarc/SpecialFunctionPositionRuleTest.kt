package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.SpecialFunctionPositionRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class SpecialFunctionPositionRuleTest : AbstractRuleTestCase<SpecialFunctionPositionRule>() {
    override fun createRule() = SpecialFunctionPositionRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<SpecialFunctionPositionRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Special function at the bottom`() =
        assertNoViolations(
            """
            class Foo {
                void bar() {}

                void baz() {}

                @Override
                String toString() {
                    return "foo"
                }

                @Override
                int hashCode() {
                    return 0
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Special function not at the bottom`() =
        assertTwoViolations(
            """
            class Foo {
                @Override
                String toString() {
                    return "foo"
                }

                void bar() {}

                @Override
                int hashCode() {
                    return 0
                }

                void baz() {}
            }
            """.trimIndent(),
            3,
            "String toString() {",
            Messages.get(MSG, "toString"),
            10,
            "int hashCode() {",
            Messages.get(MSG, "hashCode"),
        )

    @Test
    fun `Grouped overridden functions`() =
        assertNoViolations(
            """
            class Foo {
                @Override
                String toString() {
                    return "foo"
                }

                @Override
                int hashCode() {
                    return 0
                }

                @Override
                boolean equals(Object other) {
                    return false
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Skip static members`() =
        assertNoViolations(
            """
            class Foo {
                @Override
                String toString() {
                    return "foo"
                }

                public static void baz() {}
            }
            """.trimIndent(),
        )
}
