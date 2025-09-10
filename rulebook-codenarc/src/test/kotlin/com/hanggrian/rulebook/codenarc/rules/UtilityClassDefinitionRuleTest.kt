package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class UtilityClassDefinitionRuleTest : AbstractRuleTestCase<UtilityClassDefinitionRule>() {
    override fun createRule() = UtilityClassDefinitionRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<UtilityClassDefinitionRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Utility class With private constructor and final modifier`() =
        assertNoViolations(
            """
            final class Foo {
                private Foo() {}

                static foo() {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Utility classes missing private constructor or final modifier`() =
        assertTwoViolations(
            """
            final class Foo {
                static foo() {}
            }

            class Bar {
                private Bar() {}

                static bar() {}
            }
            """.trimIndent(),
            1,
            "final class Foo {",
            "Add private constructor.",
            5,
            "class Bar {",
            "Put 'final' modifier.",
        )

    @Test
    fun `Flag non-private constructors`() =
        assertTwoViolations(
            """
            final class Foo {
                Foo(int a) {}

                public Foo(int a, int b) {}

                static bar() {}
            }
            """.trimIndent(),
            2,
            "Foo(int a) {}",
            "Omit 'public' modifier from constructor.",
            4,
            "public Foo(int a, int b) {}",
            "Omit 'public' modifier from constructor.",
        )

    @Test
    fun `Skip when class has non-static members`() =
        assertNoViolations(
            """
            interface Foo {}

            class Bar implements Foo {
                static bar() {}
            }

            class Baz {
                var baz = 0

                static baz() {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Skip when class is inheriting others`() =
        assertNoViolations(
            """
            interface Foo {}

            class Bar {}

            class Baz implements Foo {
                static baz() {}
            }

            class Qux extends Bar {
                static qux() {}
            }
            """.trimIndent(),
        )
}
