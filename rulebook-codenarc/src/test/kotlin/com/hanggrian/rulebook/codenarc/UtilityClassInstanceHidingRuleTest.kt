package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.UtilityClassInstanceHidingRule.Companion.MSG_CONSTRUCTOR
import com.hanggrian.rulebook.codenarc.UtilityClassInstanceHidingRule.Companion.MSG_CONSTRUCTOR_MODIFIER
import com.hanggrian.rulebook.codenarc.UtilityClassInstanceHidingRule.Companion.MSG_MODIFIER
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class UtilityClassInstanceHidingRuleTest :
    AbstractRuleTestCase<UtilityClassInstanceHidingRule>() {
    override fun createRule() = UtilityClassInstanceHidingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<UtilityClassInstanceHidingRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Utility class with private constructor and final modifier`() =
        assertNoViolations(
            """
            final class Foo {
                private Foo() {}

                static void bar() {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Utility class without modifier and constructor`() =
        assertTwoViolations(
            """
            class Foo {
                static void bar() {}
            }
            """.trimIndent(),
            1,
            "class Foo {",
            Messages[MSG_MODIFIER],
            1,
            "class Foo {",
            Messages[MSG_CONSTRUCTOR],
        )

    @Test
    fun `Utility class with non-private constructors`() =
        assertTwoViolations(
            """
            final class Foo {
                Foo(int a) {}

                public Foo(int a, int b) {}

                static void bar() {}
            }
            """.trimIndent(),
            2,
            "Foo(int a) {}",
            Messages[MSG_CONSTRUCTOR_MODIFIER],
            4,
            "public Foo(int a, int b) {}",
            Messages[MSG_CONSTRUCTOR_MODIFIER],
        )
}
