package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class CommonFunctionPositionRuleTest : RuleTest<CommonFunctionPositionRule>() {
    override fun createRule() = CommonFunctionPositionRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<CommonFunctionPositionVisitor>(rule.astVisitor)
    }

    @Test
    fun `Special function at the bottom`() =
        assertNoViolations(
            """
            class Foo {
                def bar() {}

                def baz() {}

                @Override
                def toString() {
                    return 'foo'
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
                    return 'foo'
                }

                def bar() {}

                @Override
                int hashCode() {
                    return 0
                }

                def baz() {}
            }
            """.trimIndent(),
            3,
            "String toString() {",
            "Move 'toString' to last.",
            10,
            "int hashCode() {",
            "Move 'hashCode' to last.",
        )

    @Test
    fun `Grouped overridden functions`() =
        assertNoViolations(
            """
            class Foo {
                @Override
                String toString() {
                    return 'foo'
                }

                @Override
                int hashCode() {
                    return 0
                }

                @Override
                boolean equals(var other) {
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
                    return 'foo'
                }

                static baz() {}
            }
            """.trimIndent(),
        )
}
