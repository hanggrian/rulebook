package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.ClassMemberOrderingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class ClassMemberOrderingRuleTest : AbstractRuleTestCase<ClassMemberOrderingRule>() {
    override fun createRule() = ClassMemberOrderingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<ClassMemberOrderingRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Correct member organization`() =
        assertNoViolations(
            """
            class Foo {
                int bar = 0

                Foo() {
                    this(0)
                }

                Foo(int a) {}

                void baz() {}
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

                Foo(int a) {}

                int bar = 0
            }
            """.trimIndent(),
            8,
            "int bar = 0",
            Messages.get(MSG, "property", "constructor"),
        )

    @Test
    fun `Member constructor after function`() =
        assertTwoViolations(
            """
            class Foo {
                void baz() {}

                Foo() {
                    this(0)
                }

                Foo(int a) {}
            }
            """.trimIndent(),
            4,
            "Foo() {",
            Messages.get(MSG, "constructor", "function"),
            8,
            "Foo(int a) {}",
            Messages.get(MSG, "constructor", "function"),
        )

    @Test
    fun `Skip static members`() =
        assertNoViolations(
            """
            class Foo {
                static void baz() {}

                static baz = 0

                Foo() {}
            }
            """.trimIndent(),
        )
}
