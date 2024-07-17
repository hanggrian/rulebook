package com.hanggrian.rulebook.codenarc

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.codenarc.GenericsNameWhitelistingRule.Companion.MSG
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class GenericsNameWhitelistingRuleTest : AbstractRuleTestCase<GenericsNameWhitelistingRule>() {
    override fun createRule() = GenericsNameWhitelistingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<GenericsNameWhitelistingRule.Visitor>(rule.astVisitor)

        val rule = GenericsNameWhitelistingRule()
        rule.setNames("X, Z")
        assertThat(rule.names).containsExactly("X", "Z")
    }

    @Test
    fun `Common generic type in class`() =
        assertNoViolations(
            """
            class MyClass<T> {}

            interface MyInterface<T> {}
            """.trimIndent(),
        )

    @Test
    fun `Uncommon generic type in class`() =
        assertTwoViolations(
            """
            class MyClass<X> {}

            interface MyInterface<X> {}
            """.trimIndent(),
            1,
            "class MyClass<X> {}",
            Messages.get(MSG, "E, K, N, T, V"),
            3,
            "interface MyInterface<X> {}",
            Messages.get(MSG, "E, K, N, T, V"),
        )

    @Test
    fun `Common generic type in function`() =
        assertNoViolations(
            """
            <E> void execute(List<E> list) {}
            """.trimIndent(),
        )

    @Test
    fun `Uncommon generic type in function`() =
        assertSingleViolation(
            """
            <X> void execute(List<X> list) {}
            """.trimIndent(),
            1,
        )

    @Test
    fun `Skip inner generics`() =
        assertNoViolations(
            """
            class Foo<T> {
                class Bar<X> {
                }

                <Y> void bar() {
                }
            }
            """.trimIndent(),
        )
}
