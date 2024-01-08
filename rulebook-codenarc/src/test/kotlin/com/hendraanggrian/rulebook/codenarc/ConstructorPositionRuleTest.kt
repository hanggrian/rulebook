package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.ConstructorPositionVisitor.Companion.MSG_METHODS
import com.hendraanggrian.rulebook.codenarc.ConstructorPositionVisitor.Companion.MSG_PROPERTIES
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class ConstructorPositionRuleTest : AbstractRuleTestCase<ConstructorPositionRule>() {
    override fun createRule() = ConstructorPositionRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("ConstructorPosition", rule.name)
    }

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
            Messages[MSG_PROPERTIES],
        )

    @Test
    fun `Method before constructor`() =
        assertSingleViolation(
            """
            class Foo {
              void baz() {}

              Foo() {
                this(0)
              }

              Foo(int a) {}
            }
            """.trimIndent(),
            2,
            "void baz() {}",
            Messages[MSG_METHODS],
        )
}
