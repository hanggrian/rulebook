package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test
import kotlin.test.assertIs

class LowercaseHexRuleTest : RuleTest<LowercaseHexRule>() {
    override fun createRule() = LowercaseHexRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()
        assertIs<LowercaseHexVisitor>(rule.astVisitor)
    }

    @Test
    fun `Lowercase hexadecimal letters`() =
        assertNoViolations(
            """
            var foo = 0x00bb00

            def bar() {
                println(0xaa00cc)
            }
            """.trimIndent(),
        )

    @Test
    fun `Uppercase hexadecimal letters`() =
        assertTwoViolations(
            """
            var foo = 0X00BB00

            def bar() {
                println(0XAA00CC)
            }
            """.trimIndent(),
            1,
            "var foo = 0X00BB00",
            "Use hexadecimal '0x00bb00'.",
            4,
            "println(0XAA00CC)",
            "Use hexadecimal '0xaa00cc'.",
        )
}
