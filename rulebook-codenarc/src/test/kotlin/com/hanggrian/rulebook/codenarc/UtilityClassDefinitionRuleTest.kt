package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.UtilityClassDefinitionRule.Companion.MSG_CONSTRUCTOR
import com.hanggrian.rulebook.codenarc.UtilityClassDefinitionRule.Companion.MSG_CONSTRUCTOR_MODIFIER
import com.hanggrian.rulebook.codenarc.UtilityClassDefinitionRule.Companion.MSG_MODIFIER
import com.hanggrian.rulebook.codenarc.internals.Messages
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
            Messages[MSG_CONSTRUCTOR],
            5,
            "class Bar {",
            Messages[MSG_MODIFIER],
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
            Messages[MSG_CONSTRUCTOR_MODIFIER],
            4,
            "public Foo(int a, int b) {}",
            Messages[MSG_CONSTRUCTOR_MODIFIER],
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
