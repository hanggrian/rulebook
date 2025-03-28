package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.BlockTagOrderRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class BlockTagOrderRuleTest {
    private val assertThatCode = assertThatRule { BlockTagOrderRule() }

    @Test
    fun `Rule properties`() = BlockTagOrderRule().assertProperties()

    @Test
    fun `Correct tag layout`() =
        assertThatCode(
            """
            /**
             * @constructor lorem ipsum.
             * @property bar lorem ipsum.
             * @see http://some.url/
             */
            data class Foo(val bar: Int)
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
            data class Foo(val bar: Int)
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 4, Messages.get(MSG, "@property", "@see")),
            LintViolation(4, 4, Messages.get(MSG, "@constructor", "@property")),
        )
}
