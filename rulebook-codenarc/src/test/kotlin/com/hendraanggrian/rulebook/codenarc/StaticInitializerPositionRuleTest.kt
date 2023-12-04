package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.StaticInitializerPositionVisitor.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class StaticInitializerPositionRuleTest : AbstractRuleTestCase<StaticInitializerPositionRule>() {
    override fun createRule() = StaticInitializerPositionRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("StaticInitializerPosition", rule.name)
    }

    @Test
    fun `Correct format`() =
        assertNoViolations(
            """
            class MyClass {
              private int num = InnerClass.DEFAULT_VALUE

              MyClass() {
                num = InnerClass.DEFAULT_VALUE
              }

              void log() {
                System.out.print(InnerClass.DEFAULT_VALUE)
              }

              static class InnerClass {
                static final int DEFAULT_VALUE = 0
              }
            }
            """.trimIndent(),
        )

    @Test
    fun `Static initializer before property`() =
        assertSingleViolation(
            """
            class MyClass {
              static class InnerClass {
                static final int DEFAULT_VALUE = 0
              }

              private int num = InnerClass.DEFAULT_VALUE
            }
            """.trimIndent(),
            2,
            "static class InnerClass {",
            Messages[MSG],
        )

    @Test
    fun `Static initializer before constructor`() =
        assertSingleViolation(
            """
            class MyClass {
              static class InnerClass {
                static final int DEFAULT_VALUE = 0
              }

              MyClass() {
                System.out.print(InnerClass.DEFAULT_VALUE)
              }
            }
            """.trimIndent(),
            2,
            "static class InnerClass {",
            Messages[MSG],
        )

    @Test
    fun `Static initializer before method`() =
        assertSingleViolation(
            """
            class MyClass {
              static class InnerClass {
                static final int DEFAULT_VALUE = 0
              }

              void log() {
                System.out.print(InnerClass.DEFAULT_VALUE)
              }
            }
            """.trimIndent(),
            2,
            "static class InnerClass {",
            Messages[MSG],
        )
}
