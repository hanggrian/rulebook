package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.BlockTagOrderingRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockTagOrderingRuleTest {
    private val assertThatCode = assertThatRule { BlockTagOrderingRule() }

    @Test
    fun `Rule properties`() = BlockTagOrderingRule().assertProperties()

    @Test
    fun `Correct tag layout`() =
        assertThatCode(
            """
            /**
             * @constructor lorem ipsum.
             * @param lorem ipsum.
             * @see http://some.url/
             */
            data class Foo(bar: Int)
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Incorrect tag layout`() =
        assertThatCode(
            """
            /**
             * @see Foo
             * @property bar lorem ipsum.
             * @constructor lorem ipsum.
             */
            data class Foo(bar: Int)
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 4, Messages.get(MSG, "@property", "@see")),
            LintViolation(4, 4, Messages.get(MSG, "@constructor", "@property")),
        )
}
