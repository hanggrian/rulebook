package com.hanggrian.rulebook.codenarc.rules

import com.hanggrian.rulebook.codenarc.assertProperties
import com.hanggrian.rulebook.codenarc.violationOf
import org.codenarc.rule.AbstractRuleTestCase
import kotlin.test.Test

class MissingBlankLineBeforeBlockTagsRuleTest :
    AbstractRuleTestCase<MissingBlankLineBeforeBlockTagsRule>() {
    override fun createRule() = MissingBlankLineBeforeBlockTagsRule()

    @Test
    fun `Rule properties`() = rule.assertProperties()

    @Test
    fun `Separate summary and block tag group`() =
        assertNoViolations(
            """
            /**
             * Summary.
             *
             * @param num description.
             * @return description.
             */
            int foo(int num) {}
            """.trimIndent(),
        )

    @Test
    fun `No summary is fine`() =
        assertNoViolations(
            """
            /**
             * @param num description.
             * @return description.
             */
            int foo(int num) {}
            """.trimIndent(),
        )

    @Test
    fun `Missing empty line from summary`() =
        assertViolations(
            """
            /**
             * Summary.
             * @param num description.
             * @return description.
             */
            int foo(int num) {}

            /**
             * [Summary].
             * @param num description.
             * @return description.
             */
            int bar(int num) {}

            /**
             * `Summary`.
             * @param num description.
             * @return description.
             */
            int baz(int num) {}
            """.trimIndent(),
            violationOf(3, "* @param num description.", "Add blank line before block tag group."),
            violationOf(10, "* @param num description.", "Add blank line before block tag group."),
            violationOf(17, "* @param num description.", "Add blank line before block tag group."),
        )
}
