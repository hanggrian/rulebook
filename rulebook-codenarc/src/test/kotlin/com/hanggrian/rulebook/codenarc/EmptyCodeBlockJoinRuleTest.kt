package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class EmptyCodeBlockJoinRuleTest : AbstractRuleTestCase<EmptyCodeBlockJoinRule>() {
    override fun createRule() = EmptyCodeBlockJoinRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    override fun testThatUnrelatedCodeHasNoViolations() {}

    @Test
    fun `Wrapped empty block`() =
        assertNoViolations(
            """
            class Foo {}

            def bar() {}
            """.trimIndent(),
        )

    @Test
    fun `Unwrapped empty block`() =
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

    @Test
    fun `Allow code block with comment`() =
        assertNoViolations(
            """
            class Foo {
                // Lorem ipsum.
            }
            """.trimIndent(),
        )
}
