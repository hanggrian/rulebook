package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.InnerClassPositionRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
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
                int bar = 0

                Foo(int a) {}

                Foo() {
                    this(0)
                }

                void baz() {}

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
                class Inner {}

                int bar = 0

                class AnotherInner {}

                void baz() {
                    print(0)
                }
            }
            """.trimIndent(),
            2,
            "class Inner {}",
            Messages[MSG],
            6,
            "class AnotherInner {}",
            Messages[MSG],
        )
}
