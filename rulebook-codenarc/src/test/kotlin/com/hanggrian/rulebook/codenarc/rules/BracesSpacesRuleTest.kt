package com.hanggrian.rulebook.codenarc.rules

import kotlin.test.Test

class BracesSpacesRuleTest : RuleTest<BracesSpacesRule>() {
    override fun createRule() = BracesSpacesRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Spaced braces`() =
        assertNoViolations(
            """
            class Foo { var a = 0 }

            def bar() { var b = 0 }
            """.trimIndent(),
        )

    @Test
    fun `Unspaced braces`() =
        assertTwoViolations(
            """
            class Foo {var a = 0 }

            def bar() { var b = 0}
            """.trimIndent(),
            1,
            "class Foo {var a = 0 }",
            "Add space inside single-line braces.",
            3,
            "def bar() { var b = 0}",
            "Add space inside single-line braces.",
        )

    @Test
    fun `Skip empty braces`() =
        assertNoViolations(
            """
            class Foo {}

            def bar() {}
            """.trimIndent(),
        )

    @Test
    fun `Skip array initialization`() =
        assertNoViolations(
            """
            class Foo {
                var bar = new int[]{0}
            }
            """.trimIndent(),
        )
}
