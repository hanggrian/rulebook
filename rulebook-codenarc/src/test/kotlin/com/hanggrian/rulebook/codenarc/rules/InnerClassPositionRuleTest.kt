package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class InnerClassPositionRuleTest : AbstractRuleTestCase<InnerClassPositionRule>() {
    override fun createRule() = InnerClassPositionRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<InnerClassPositionRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Inner classes at the bottom`() =
        assertNoViolations(
            """
            class Foo {
                var bar = 0

                Foo(var a) {}

                Foo() {
                    this(0)
                }

                def baz() {}

                class Inner {}

                class AnotherInner {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Inner classes before members`() =
        assertTwoViolations(
            """
            class Foo {
                interface Inner {}

                var bar = 0

                class AnotherInner {}

                def baz() {
                    print(0)
                }
            }
            """.trimIndent(),
            2,
            "interface Inner {}",
            "Move inner class to the bottom.",
            6,
            "class AnotherInner {}",
            "Move inner class to the bottom.",
        )

    @Test
    fun `Skip enum members with initialization`() =
        assertNoViolations(
            """
            enum Foo {
                BAR {
                    @Override
                    def baz() {}
                };

                abstract def baz()
            }
            """.trimIndent(),
        )
}
