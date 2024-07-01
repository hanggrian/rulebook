package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.SpecialFunctionPositionRule.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class SpecialFunctionPositionRuleTest : AbstractRuleTestCase<SpecialFunctionPositionRule>() {
    override fun createRule() = SpecialFunctionPositionRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

    @Test
    fun `Overridden function at the bottom`() =
        assertNoViolations(
            """
            class Foo {
                void bar() {}

                @Override
                String toString() {
                    return "baz"
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Overridden class before function`() =
        assertSingleViolation(
            """
            class Foo {
                @Override
                String toString() {
                    return "baz"
                }

                void bar() {}
            }
            """.trimIndent(),
            3,
            "String toString() {",
            Messages.get(MSG, "toString"),
        )

    @Test
    fun `Grouping overridden functions`() =
        assertNoViolations(
            """
            class Foo {
                @Override
                String toString() {
                    return "baz"
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
                    return "bar"
                }

                public static void baz() {}
            }
            """.trimIndent(),
        )
}
