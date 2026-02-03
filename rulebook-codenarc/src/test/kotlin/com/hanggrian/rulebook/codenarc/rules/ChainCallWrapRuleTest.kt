package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class ChainCallWrapRuleTest : AbstractRuleTestCase<ChainCallWrapRule>() {
    override fun createRule() = ChainCallWrapRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<ChainCallWrapVisitor>(rule.astVisitor)
    }

    @Test
    fun `Aligned chain method call continuation`() =
        assertNoViolations(
            """
            def foo() {
                new StringBuilder(
                    'Lorem ipsum',
                ).append(0)
                    .append(1)
            }

            def bar() {
                baz()
                    .baz(
                        new String('Lorem ipsum'),
                    ).baz(
                        new String('Lorem ipsum'),
                    )
            }

            Baz baz() {
                return Baz()
            }

            class Baz {
                Baz baz(String s) {
                    return this
                }

                Baz baz() {
                    return this
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Misaligned chain method call continuation`() =
        assertTwoViolations(
            """
            def foo() {
                new StringBuilder(
                    'Lorem ipsum',
                )
                    .append(0)
                    .append(2)
            }

            def bar() {
                baz()
                    .baz(
                        new String('Lorem ipsum'),
                    )
                    .baz(
                        new String('Lorem ipsum'),
                    )
            }

            Baz baz() {
                return Baz()
            }

            class Baz {
                Baz baz(String s) {
                    return this
                }

                Baz baz() {
                    return this
                }
            }
            """.trimIndent(),
            5,
            ".append(0)",
            "Omit newline before '.'.",
            14,
            ".baz(",
            "Omit newline before '.'.",
        )

    @Test
    fun `Inconsistent dot position`() =
        assertTwoViolations(
            """
            def foo() {
                new StringBuilder('Lorem ipsum')
                    .append(0).append(1)
                    .append(2)
            }

            def bar() {
                baz().baz()
                    .baz()
            }

            Baz baz() {
                return Baz()
            }

            class Baz {
                Baz baz(String s) {
                    return this
                }

                Baz baz() {
                    return this
                }
            }
            """.trimIndent(),
            3,
            ".append(0).append(1)",
            "Put newline before '.'.",
            8,
            "baz().baz()",
            "Put newline before '.'.",
        )

    @Test
    fun `Also capture non-method call`() =
        assertSingleViolation(
            """
            def foo() {
                baz()
                    .baz().qux
                    .baz()
            }

            Baz baz() {
                return Baz()
            }

            class Baz {
                Baz qux = this;

                Baz baz(String s) {
                    return this
                }

                Baz baz() {
                    return this
                }
            }
            """.trimIndent(),
            3,
            ".baz().qux",
            "Put newline before '.'.",
        )

    @Test
    fun `Allow dots on single-line`() =
        assertNoViolations(
            """
            def foo() {
                new StringBuilder(
                    0,
                ).append(1).append(2)
            }

            def bar() {
                new StringBuilder(0).append(1).append(
                    2,
                )
            }
            """.trimIndent(),
        )
}
