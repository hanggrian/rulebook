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
    fun `Correct format`() =
        assertNoViolations(
            """
            class MyClass {
              private int num

              public MyClass() {
                num = 0
              }

              public void increment() {
                num++
              }
            }
            """.trimIndent(),
        )

    @Test
    fun `Property after constructor`() =
        assertSingleViolation(
            """
            class MyClass {
              public MyClass() {
                num = 0
              }

              private int num
            }
            """.trimIndent(),
            2,
            "public MyClass() {",
            Messages[MSG_PROPERTIES],
        )

    @Test
    fun `Method before constructor`() =
        assertSingleViolation(
            """
            class MyClass {
              private int num

              public void log() {
                System.out.println(num)
              }

              public MyClass() {
                num = 0
              }
            }
            """.trimIndent(),
            8,
            "public MyClass() {",
            Messages[MSG_METHODS],
        )
}
