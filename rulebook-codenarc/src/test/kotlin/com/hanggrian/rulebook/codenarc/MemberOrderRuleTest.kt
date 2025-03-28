package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.MemberOrderRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class MemberOrderRuleTest : AbstractRuleTestCase<MemberOrderRule>() {
    override fun createRule() = MemberOrderRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<MemberOrderRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Correct member layout`() =
        assertNoViolations(
            """
            class Foo {
                var bar = 0

                Foo() {
                    this(0)
                }

                Foo(var a) {}

                def baz() {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Member property after constructor`() =
        assertSingleViolation(
            """
            class Foo {
                Foo() {
                    this(0)
                }

                Foo(var a) {}

                var bar = 0
            }
            """.trimIndent(),
            8,
            "var bar = 0",
            Messages.get(MSG, "property", "constructor"),
        )

    @Test
    fun `Member constructor after function`() =
        assertTwoViolations(
            """
            class Foo {
                def baz() {}

                Foo() {
                    this(0)
                }

                Foo(var a) {}
            }
            """.trimIndent(),
            4,
            "Foo() {",
            Messages.get(MSG, "constructor", "function"),
            8,
            "Foo(var a) {}",
            Messages.get(MSG, "constructor", "function"),
        )

    @Test
    fun `Skip static members`() =
        assertNoViolations(
            """
            class Foo {
                static baz() {}

                static var baz = 0

                Foo() {}
            }
            """.trimIndent(),
        )
}
