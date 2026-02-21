package com.hanggrian.rulebook.codenarc.rules

import com.google.common.truth.Truth.assertThat
import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test

class BlockTagPunctuationRuleTest : RuleTest<BlockTagPunctuationRule>() {
    override fun createRule() = BlockTagPunctuationRule()

    @Test
    fun `Rule properties`() {
        rule.assertProperties()

        rule.setTags("@author, @see")
        assertThat(rule.tagSet).containsExactly("@author", "@see")
    }

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
    fun `Descriptions end without a period`() =
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
