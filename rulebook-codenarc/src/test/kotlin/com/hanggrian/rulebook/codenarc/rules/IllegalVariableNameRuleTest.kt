package com.hanggrian.rulebook.codenarc.rules

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class IllegalVariableNameRuleTest : AbstractRuleTestCase<IllegalVariableNameRule>() {
    override fun createRule() = IllegalVariableNameRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<IllegalVariableNameVisitor>(rule.astVisitor)

        val rule = IllegalVariableNameRule()
        rule.names = "foo, bar"
        assertThat(rule.nameList).containsExactly("foo", "bar")
    }

    @Test
    fun `Descriptive property names`() =
        assertNoViolations(
            """
            class Foo {
                var age = 0
                String name = ''
            }
            """.trimIndent(),
        )

    @Test
    fun `Prohibited property names`() =
        assertTwoViolations(
            """
            class Foo {
                var integer = 0
                String string = ''
            }
            """.trimIndent(),
            2,
            "var integer = 0",
            "Use descriptive name.",
            3,
            "String string = ''",
            "Use descriptive name.",
        )

    @Test
    fun `Descriptive parameter names`() =
        assertNoViolations(
            """
            def foo(int age) {
                [0].each { name ->
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Prohibited parameter names`() =
        assertTwoViolations(
            """
            def foo(int integer) {
                [0].each { string ->
                }
            }
            """.trimIndent(),
            1,
            "def foo(int integer) {",
            "Use descriptive name.",
            2,
            "[0].each { string ->",
            "Use descriptive name.",
        )

    @Test
    fun `Descriptive de-structuring property names`() =
        assertNoViolations(
            """
            def foo() {
                def (age, name) = [0, '']
            }
            """.trimIndent(),
        )

    @Test
    fun `Prohibited de-structuring property names`() =
        assertTwoViolations(
            """
            def foo() {
                def (integer, string) = [0, '']
            }
            """.trimIndent(),
            2,
            "def (integer, string) = [0, '']",
            "Use descriptive name.",
            2,
            "def (integer, string) = [0, '']",
            "Use descriptive name.",
        )
}
