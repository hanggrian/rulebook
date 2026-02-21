package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.RuleTest
import kotlin.test.Test

class BlockTagOrderRuleTest : RuleTest<BlockTagOrderRule>() {
    override fun createRule() = BlockTagOrderRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Correct tag layout`() =
        assertNoViolations(
            """
            /**
             * @param bar lorem ipsum.
             * @return lorem ipsum.
             * @see http://some.url/
             */
            def foo(var bar) {}
            """.trimIndent(),
        )

    @Test
    fun `Incorrect tag layout`() =
        assertTwoViolations(
            """
            /**
             * @see http://some.url/
             * @return lorem ipsum.
             * @param bar lorem ipsum.
             */
            def foo(var bar) {}
            """.trimIndent(),
            3,
            "* @return lorem ipsum.",
            "Arrange tag '@return' before '@see'.",
            4,
            "* @param bar lorem ipsum.",
            "Arrange tag '@param' before '@return'.",
        )

    @Test
    fun `Aware of duplicate block tag`() =
        assertNoViolations(
            """
            /**
             * @param bar lorem ipsum.
             * @param baz lorem ipsum.
             */
            def foo(var bar, var baz) {}
            """.trimIndent(),
        )
}
