package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class GenericNameRuleTest : AbstractRuleTestCase<GenericNameRule>() {
    override fun createRule() = GenericNameRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<RequiredGenericsNameVisitor>(rule.astVisitor)
    }

    @Test
    fun `Correct generic name in class`() =
        assertNoViolations(
            """
            class MyClass<T> {}

            interface MyInterface<E> {}
            """.trimIndent(),
        )

    @Test
    fun `Incorrect generic name in class`() =
        assertTwoViolations(
            """
            class MyClass<XA> {}

            interface MyInterface<Xa> {}
            """.trimIndent(),
            1,
            "class MyClass<XA> {}",
            "Use single uppercase letter.",
            3,
            "interface MyInterface<Xa> {}",
            "Use single uppercase letter.",
        )

    @Test
    fun `Correct generic name in function`() =
        assertNoViolations(
            """
            <E> void execute(List<E> list) {}
            """.trimIndent(),
        )

    @Test
    fun `Incorrect generic name in function`() =
        assertSingleViolation(
            """
            <Xa> void execute(List<Xa> list) {}
            """.trimIndent(),
            1,
            "<Xa> void execute(List<Xa> list) {}",
            "Use single uppercase letter.",
        )

    @Test
    fun `Skip multiple generics`() =
        assertNoViolations(
            """
            class Foo<Xa, Ax> {}

            fun <Bar, Baz> bar(list: List<Bar, Baz>) {}
            """.trimIndent(),
        )

    @Test
    fun `Skip inner generics`() =
        assertNoViolations(
            """
            class Foo<T> {
                class Bar<X> {}

                <Y> void bar() {}
            }
            """.trimIndent(),
        )
}
