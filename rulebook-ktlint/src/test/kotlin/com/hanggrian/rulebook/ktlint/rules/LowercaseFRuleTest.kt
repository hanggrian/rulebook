package com.hanggrian.rulebook.ktlint.rules

import com.hanggrian.rulebook.ktlint.RuleTest
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class LowercaseFRuleTest : RuleTest() {
    private val assertThatCode = assertThatRule { LowercaseFRule() }

    @Test
    fun `Rule properties`() = LowercaseFRule().assertProperties()

    @Test
    fun `Lowercase literal floats`() =
        assertThatCode(
            """
            val foo = 0f

            fun bar() {
                println(123f)
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Uppercase literal floats`() =
        assertThatCode(
            """
            val foo = 0F

            fun bar() {
                println(123F)
            }
            """.trimIndent(),
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(1, 13, "Tag float literal by 'f'."),
            LintViolation(4, 17, "Tag float literal by 'f'."),
        )
}
