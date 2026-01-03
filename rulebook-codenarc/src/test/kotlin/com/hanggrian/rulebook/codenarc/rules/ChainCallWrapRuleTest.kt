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

            def baz() {
                new Bar()
                    .baz(
                        new String('Lorem ipsum'),
                    ).baz(
                        new String('Lorem ipsum'),
                    )
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
                new Bar()
                    .baz(
                        new String('Lorem ipsum'),
                    )
                    .baz(
                        new String('Lorem ipsum'),
                    )
            }

            class Baz {
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
                new StringBuilder(
                    'Lorem ipsum'
                ).append(0).append(2)
            }

            def bar() {
                new Baz().baz()
                    .baz()
            }

            class Baz {
                Baz baz() {
                    return this
                }
            }
            """.trimIndent(),
            4,
            ").append(0).append(2)",
            "Put newline before '.'.",
            8,
            "new Baz().baz()",
            "Put newline before '.'.",
        )

    @Test
    fun `Also capture non-method call`() =
        assertSingleViolation(
            """
            def foo() {
                new Baz()
                    .baz().baz
                    .baz()
            }

            class Baz {
                Baz baz = this

                Baz baz() {
                    return this
                }
            }
            """.trimIndent(),
            3,
            ".baz().baz",
            "Put newline before '.'.",
        )

    @Test
    fun `Allow single call`() =
        assertNoViolations(
            """
            def foo(Runnable r) {
                ChainCallWrapCheck().foo(() -> {
                    println()
                    println()
                })
                ChainCallWrapCheck().foo {
                    println()
                    println()
                }
            }
            """.trimIndent(),
        )
}
