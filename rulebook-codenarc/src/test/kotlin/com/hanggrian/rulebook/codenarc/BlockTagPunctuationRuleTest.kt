package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class BlockTagPunctuationRuleTest : AbstractRuleTestCase<BlockTagPunctuationRule>() {
    override fun createRule() = BlockTagPunctuationRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `No description`() =
        assertNoViolations(
            """
            /**
             * @param
             * @return
             */
            def add(var num) {}
            """.trimIndent(),
        )

    @Test
    fun `Descriptions end with a period`() =
        assertNoViolations(
            """
            /**
             * @param num value.
             * @return total value.
             */
            def add(var num) {}
            """.trimIndent(),
        )

    @Test
    fun `Descriptions don't end with a period`() =
        assertTwoViolations(
            """
            /**
             * @param num value
             * @return total value
             */
            def add(var num) {}
            """.trimIndent(),
            2,
            "@param num value",
            "End '@param' with a period.",
            3,
            "@return total value",
            "End '@return' with a period.",
        )

    @Test
    fun `Long descriptions`() =
        assertTwoViolations(
            """
            /**
             * @param num
             *   value
             * @return total
             *   value
             */
            def add(var num) {}
            """.trimIndent(),
            3,
            "*   value",
            "End '@param' with a period.",
            5,
            "*   value",
            "End '@return' with a period.",
        )
}
