package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.BlockTagIndentationRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockTagIndentationRuleTest {
    private val assertThatCode = assertThatRule { BlockTagIndentationRule() }

    @Test
    fun `Rule properties`() = BlockTagIndentationRule().assertProperties()

    @Test
    fun `Indented block tag description`() =
        assertThatCode(
            """
            /**
             * @constructor lorem
             *     ipsum.
             * @param bar lorem
             *     ipsum.
             */
            data class Foo(val bar: Int)
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Unindented block tag description`() =
        assertThatCode(
            """
            /**
             * @constructor lorem
             *  ipsum.
             * @param bar lorem
             *   ipsum.
             */
            data class Foo(val bar: Int)
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 3, Messages.get(MSG, "4 spaces")),
            LintViolation(5, 3, Messages.get(MSG, "4 spaces")),
        )
}
