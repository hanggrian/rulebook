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
            float foo = 0f

            void bar() {
                println(123f)
            }
            """.trimIndent(),
        )

    @Test
    fun `Uppercase literal floats`() =
        assertTwoViolations(
            """
            float foo = 0F

            void bar() {
                println(123F)
            }
            """.trimIndent(),
            1,
            "float foo = 0F",
            Messages[MSG_NUM],
            4,
            "println(123F)",
            Messages[MSG_NUM],
        )

    @Test
    fun `Lowercase literal hexadecimals`() =
        assertTwoViolations(
            """
            float foo = 0x00f

            void bar() {
                println(0x123f)
            }
            """.trimIndent(),
            1,
            "float foo = 0x00f",
            Messages[MSG_HEX],
            4,
            "println(0x123f)",
            Messages[MSG_HEX],
        )

    @Test
    fun `Uppercase literal hexadecimals`() =
        assertNoViolations(
            """
            float foo = 0x00F

            void bar() {
                println(0x123F)
            }
            """.trimIndent(),
        )
}
