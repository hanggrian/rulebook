package com.hanggrian.rulebook.codenarc.rules

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class MemberOrderRuleTest : RuleTest<MemberOrderRule>() {
    override fun createRule() = MemberOrderRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<MemberOrderVisitor>(rule.astVisitor)

        rule.setOrder("static, function, constructor, property")
        assertThat(rule.orderSet).containsExactly("static", "function", "constructor", "property")
    }

    @Test
    fun `Correct member layout`() =
        assertNoViolations(
            """
            class Foo {
                var bar = 0

                Foo() {
                    this(0)
                }

                Foo(var a) {}

                def baz() {}

                static def qux() {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Property after constructor`() =
        assertSingleViolation(
            """
            class Foo {
                Foo() {}

                var bar = 0
            }
            """.trimIndent(),
            4,
            "var bar = 0",
            "Arrange member 'property' before 'constructor'.",
        )

    @Test
    fun `Constructor after function`() =
        assertSingleViolation(
            """
            class Foo {
                def baz() {}

                Foo() {}
            }
            """.trimIndent(),
            4,
            "Foo() {",
            "Arrange member 'constructor' before 'function'.",
        )

    @Test
    fun `Function after static member`() =
        assertSingleViolation(
            """
            class Foo {
                static def qux() {}

                def baz() {}
            }
            """.trimIndent(),
            4,
            "def baz() {}",
            "Arrange member 'function' before 'static member'.",
        )
}
