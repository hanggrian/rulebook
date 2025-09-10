package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.assertProperties
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
            class Foo(val bar: Int)
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
            class Foo(val bar: Int)
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 3, "Indent block tag description by '4 spaces'."),
            LintViolation(5, 3, "Indent block tag description by '4 spaces'."),
        )
}
