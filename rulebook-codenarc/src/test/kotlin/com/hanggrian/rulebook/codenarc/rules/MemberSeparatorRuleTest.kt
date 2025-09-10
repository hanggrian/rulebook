package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class MemberSeparatorRuleTest : AbstractRuleTestCase<MemberSeparatorRule>() {
    override fun createRule() = MemberSeparatorRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<MemberSeparatorRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Single-line members with separator`() =
        assertNoViolations(
            """
            class Foo {
                var bar = 1

                Foo() {}

                def baz() {}
            }
            """.trimIndent(),
        )

    @Test
    fun `Single-line members without separator`() =
        assertTwoViolations(
            """
            class Foo {
                var bar = 1
                Foo() {}
                def baz() {}
            }
            """.trimIndent(),
            2,
            "var bar = 1",
            "Add blank line after 'property'.",
            3,
            "Foo() {}",
            "Add blank line after 'constructor'.",
        )

    @Test
    fun `Multiline members with separator`() =
        assertNoViolations(
            """
            class Foo {
                var bar =
                    1 +
                        2

                Foo() {
                    super()
                }

                def baz() {
                    println()
                }
            }
            """.trimIndent(),
        )

    @Test
    fun `Multiline members without separator`() =
        assertTwoViolations(
            """
            class Foo {
                var bar =
                    1 +
                        2
                Foo() {
                    super()
                }
                def baz() {
                    println()
                }
            }
            """.trimIndent(),
            4,
            "2",
            "Add blank line after 'property'.",
            7,
            "}",
            "Add blank line after 'constructor'.",
        )

    @Test
    fun `Skip fields grouped together`() =
        assertNoViolations(
            """
            class Foo {
                var bar = 1
                var baz = 2
                var qux =
                    3 +
                        4
            }
            """.trimIndent(),
        )

    @Test
    fun `Capture members with comments`() =
        assertTwoViolations(
            """
            class Foo {
                // Comment
                var bar =
                    1 +
                        2
                /** Short block comment */
                Foo() {
                    super()
                }
                /**
                 * Long block comment
                 */
                def baz() {
                    println()
                }
            }
            """.trimIndent(),
            5,
            "2",
            "Add blank line after 'property'.",
            9,
            "}",
            "Add blank line after 'constructor'.",
        )
}
