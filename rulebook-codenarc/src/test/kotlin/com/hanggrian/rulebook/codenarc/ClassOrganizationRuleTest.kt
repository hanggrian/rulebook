package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.ClassOrganizationRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class ClassOrganizationRuleTest : AbstractRuleTestCase<ClassOrganizationRule>() {
    override fun createRule() = ClassOrganizationRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

    @Test
    fun `Properties, initializers, constructors, and methods`() =
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
    fun `Property after constructor`() =
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
    fun `Constructor after function`() =
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
