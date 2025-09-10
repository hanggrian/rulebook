package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
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
            class Foo(val bar: Int)
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Incorrect tag layout`() =
        assertThatCode(
            """
            /**
             * @see http://some.url/
             * @property bar lorem ipsum.
             * @constructor lorem ipsum.
             */
            class Foo(val bar: Int)
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 4, "Arrange tag '@property' before '@see'."),
            LintViolation(4, 4, "Arrange tag '@constructor' before '@property'."),
        )

    @Test
    fun `Aware of duplicate block tag`() =
        assertThatCode(
            """
            /**
             * @property bar lorem ipsum.
             * @property baz lorem ipsum.
             */
            class Foo(val bar: Int, val baz: Int)
            """.trimIndent(),
        ).hasNoLintViolations()
}
