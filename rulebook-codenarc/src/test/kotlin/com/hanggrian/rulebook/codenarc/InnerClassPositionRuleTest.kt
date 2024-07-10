package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.InnerClassPositionRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class InnerClassPositionRuleTest : AbstractRuleTestCase<InnerClassPositionRule>() {
    override fun createRule() = InnerClassPositionRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

    @Test
    fun `Inner class at the bottom`() =
        assertNoViolations(
            """
            class Foo {
                int bar = 0

                Foo(int a) {}

                Foo() {
                    this(0)
                }

                void baz() {}

                class Bar {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Inner class before property`() =
        assertSingleViolation(
            """
            class Foo {
                class Bar {}

                int bar = 0
            }
            """.trimIndent(),
            2,
            "class Bar {}",
            Messages[MSG],
        )

    @Test
    fun `Inner class before constructor`() =
        assertSingleViolation(
            """
            class Foo {
                class Bar {}

                Foo(int a) {}

                Foo() {
                  this(0)
                }
            }
            """.trimIndent(),
            2,
            "class Bar {}",
            Messages[MSG],
        )

    @Test
    fun `Inner class before method`() =
        assertSingleViolation(
            """
            class Foo {
                class Bar {}

                void baz() {}
            }
            """.trimIndent(),
            2,
            "class Bar {}",
            Messages[MSG],
        )
}
