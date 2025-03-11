package com.hanggrian.rulebook.codenarc

import com.hanggrian.rulebook.codenarc.FloatLiteralTaggingRule.Companion.MSG_HEX
import com.hanggrian.rulebook.codenarc.FloatLiteralTaggingRule.Companion.MSG_NUM
import com.hanggrian.rulebook.codenarc.internals.Messages
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test
import kotlin.test.assertIs

class FloatLiteralTaggingRuleTest : AbstractRuleTestCase<FloatLiteralTaggingRule>() {
    override fun createRule() = FloatLiteralTaggingRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        assertIs<FloatLiteralTaggingRule.Visitor>(rule.astVisitor)
    }

    @Test
    fun `Lowercase literal floats`() =
        assertNoViolations(
            """
            var foo = 0f

            def bar() {
                println(123f)
            }
            """.trimIndent(),
        )

    @Test
    fun `Uppercase literal floats`() =
        assertTwoViolations(
            """
            var foo = 0F

            def bar() {
                println(123F)
            }
            """.trimIndent(),
            1,
            "var foo = 0F",
            Messages[MSG_NUM],
            4,
            "println(123F)",
            Messages[MSG_NUM],
        )

    @Test
    fun `Lowercase literal hexadecimals`() =
        assertTwoViolations(
            """
            var foo = 0x00f

            def bar() {
                println(0x123f)
            }
            """.trimIndent(),
            1,
            "var foo = 0x00f",
            Messages[MSG_HEX],
            4,
            "println(0x123f)",
            Messages[MSG_HEX],
        )

    @Test
    fun `Uppercase literal hexadecimals`() =
        assertNoViolations(
            """
            var foo = 0x00F

            def bar() {
                println(0x123F)
            }
            """.trimIndent(),
        )
}
