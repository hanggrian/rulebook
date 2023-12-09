package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.StaticClassPositionVisitor.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class StaticClassPositionRuleTest : AbstractRuleTestCase<StaticClassPositionRule>() {
    override fun createRule() = StaticClassPositionRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("StaticClassPosition", rule.name)
    }

    @Test
    fun `Static class at the bottom`() =
        assertNoViolations(
            """
            class Foo {
                int bar = VALUE

                Foo(int a) {}

                Foo() {
                  this(VALUE)
                }

                void baz() {
                  System.out.println(VALUE)
                }

                static class Bar {
                  static int VALUE = 0
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Static class before property`() =
        assertSingleViolation(
            """
            class Foo {
                static class Bar {
                  static int VALUE = 0
                }

                int bar = VALUE
            }
            """.trimIndent(),
            2,
            "static class Bar {",
            Messages[MSG],
        )

    @Test
    fun `Static class before constructor`() =
        assertSingleViolation(
            """
            class Foo {
                static class Bar {
                  static int VALUE = 0
                }

                Foo(int a) {}

                Foo() {
                  this(VALUE)
                }
            }
            """.trimIndent(),
            2,
            "static class Bar {",
            Messages[MSG],
        )

    @Test
    fun `Static class before method`() =
        assertSingleViolation(
            """
            class Foo {
                static class Bar {
                  static int VALUE = 0
                }

                void baz() {
                  System.out.println(VALUE)
                }
            }
            """.trimIndent(),
            2,
            "static class Bar {",
            Messages[MSG],
        )
}
