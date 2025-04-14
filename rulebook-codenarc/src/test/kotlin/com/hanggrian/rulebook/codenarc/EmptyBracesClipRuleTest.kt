package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class EmptyBracesClipRuleTest : AbstractRuleTestCase<EmptyBracesClipRule>() {
    override fun createRule() = EmptyBracesClipRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    override fun testThatUnrelatedCodeHasNoViolations() {}

    @Test
    fun `Wrapped braces`() =
        assertNoViolations(
            """
            class Foo {}

            def bar() {}
            """.trimIndent(),
        )

    @Test
    fun `Unwrapped braces`() =
        assertTwoViolations(
            """
            class Foo { }

            def bar() {
            }
            """.trimIndent(),
            1,
            "class Foo { }",
            "Convert into '{}'.",
            3,
            "def bar() {",
            "Convert into '{}'.",
        )

    @Test
    fun `Allow braces with comment`() =
        assertNoViolations(
            """
            class Foo {
                // Lorem ipsum.
            }
            """.trimIndent(),
        )

    @Test
    fun `Control flows with multi-blocks`() =
        assertNoViolations(
            """
            def foo() {
                try {
                } catch (Exception e) {
                }

                if (true) {
                } else if (false) {
                } else {
                }

                do {
                } while (true)
            }
            """.trimIndent(),
        )
}
