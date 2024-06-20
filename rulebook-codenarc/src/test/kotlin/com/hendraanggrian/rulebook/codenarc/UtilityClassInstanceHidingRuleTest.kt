package com.hendraanggrian.rulebook.codenarc

import com.hendraanggrian.rulebook.codenarc.UtilityClassInstanceHidingRule.Companion.MSG_EXIST
import com.hendraanggrian.rulebook.codenarc.UtilityClassInstanceHidingRule.Companion.MSG_NEW
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class UtilityClassInstanceHidingRuleTest :
    AbstractRuleTestCase<UtilityClassInstanceHidingRule>() {
    override fun createRule() = UtilityClassInstanceHidingRule()

    @Test
    fun `Rule properties`(): Unit = rule.assertProperties()

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
