package com.hendraanggrian.rulebook

import com.hendraanggrian.rulebook.codenarc.RenameUncommonGenericsRule
import com.hendraanggrian.rulebook.codenarc.RenameUncommonGenericsVisitor.Companion.MSG
import com.hendraanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertEquals

class RenameUncommonGenericsRuleTest : AbstractRuleTestCase<RenameUncommonGenericsRule>() {
    override fun createRule(): RenameUncommonGenericsRule = RenameUncommonGenericsRule()

    @Test
    fun `Rule properties`() {
        assertEquals(3, rule.priority)
        assertEquals("RenameUncommonGenerics", rule.name)
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
            2,
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
