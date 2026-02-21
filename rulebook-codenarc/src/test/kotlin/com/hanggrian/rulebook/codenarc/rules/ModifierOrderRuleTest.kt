package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test

class ModifierOrderRuleTest : RuleTest<ModifierOrderRule>() {
    override fun createRule() = ModifierOrderRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Correct modifier order`() =
        assertNoViolations(
            """
            class Foo {
                public static final class Bar {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Incorrect modifier order`() =
        assertSingleViolation(
            """
            class Foo {
                final static public class Foo {}
            }
            """.trimIndent(),
            2,
            "final static public class Foo {}",
            "Set modifiers to 'public static final'.",
        )
}
