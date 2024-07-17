package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.UtilityClassConstructorHidingRule.Companion.MSG_EXIST
import com.hanggrian.rulebook.codenarc.UtilityClassConstructorHidingRule.Companion.MSG_NEW
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class UtilityClassConstructorHidingRuleTest :
    AbstractRuleTestCase<UtilityClassConstructorHidingRule>() {
    override fun createRule() = UtilityClassConstructorHidingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<UtilityClassConstructorHidingRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Utility class with private constructor`() =
        assertNoViolations(
            """
            class Foo {
                private Foo() {}

                static void bar() {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Utility class without constructor`() =
        assertSingleViolation(
            """
            class Foo {
                static void bar() {}
            }
            """.trimIndent(),
            1,
            "class Foo {",
            Messages[MSG_NEW],
        )

    @Test
    fun `Utility class with non-private constructors`() =
        assertTwoViolations(
            """
            class Foo {
                Foo(int a) {}

                public Foo(int a, int b) {}

                static void bar() {}
            }
            """.trimIndent(),
            2,
            "Foo(int a) {}",
            Messages[MSG_EXIST],
            4,
            "public Foo(int a, int b) {}",
            Messages[MSG_EXIST],
        )
}
