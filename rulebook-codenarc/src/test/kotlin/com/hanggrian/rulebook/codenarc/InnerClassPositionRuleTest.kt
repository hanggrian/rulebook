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
            Messages[MSG],
            6,
            "class AnotherInner {}",
            Messages[MSG],
        )

    @Test
    fun `Skip enum members with initialization (Groovy only)`() =
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
