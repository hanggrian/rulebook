package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class BlockTagIndentationRuleTest : AbstractRuleTestCase<BlockTagIndentationRule>() {
    override fun createRule() = BlockTagIndentationRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Indented block tag description`() =
        assertNoViolations(
            """
            /**
             * @param bar lorem
             *     ipsum.
             * @return lorem
             *     ipsum.
             */
            def foo(var bar) {}
            """.trimIndent(),
        )

    @Test
    fun `Unindented block tag description`() =
        assertTwoViolations(
            """
            /**
             * @param bar lorem
             *  ipsum.
             * @return lorem
             *   ipsum.
             */
            def foo(var bar) {}
            """.trimIndent(),
            2,
            "@param bar lorem",
            "Indent block tag description by '4 spaces'.",
            4,
            "@return lorem",
            "Indent block tag description by '4 spaces'.",
        )
}
