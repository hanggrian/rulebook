package com.hanggrian.rulebook.codenarc

import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class ShortBlockCommentClipRuleTest : AbstractRuleTestCase<ShortBlockCommentClipRule>() {
    override fun createRule() = ShortBlockCommentClipRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Correct block comments`() =
        assertNoViolations(
            """
            /** Short comment. */
            def foo() {}

            /**
             * Very long
             * comment.
             */
            def bar() {}
            """.trimIndent(),
        )

    @Test
    fun `Multi-line block comment that can be converted into single-line`() =
        assertSingleViolation(
            """
            /**
             * Short comment.
             */
            def foo() {}
            """.trimIndent(),
            1,
            "/**",
            "Convert into single-line.",
        )

    @Test
    fun `Skip tagged block comment`() =
        assertNoViolations(
            """
            /**
             * @param bar some value.
             */
            def foo(var bar) {}
            """.trimIndent(),
        )
}
