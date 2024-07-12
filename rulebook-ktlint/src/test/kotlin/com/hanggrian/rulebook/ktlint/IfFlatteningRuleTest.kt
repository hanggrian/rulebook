package com.hanggrian.rulebook.ktlint

import com.hanggrian.rulebook.ktlint.IfFlatteningRule.Companion.MSG
import com.hanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class IfFlatteningRuleTest {
    private val assertThatCode = assertThatRule { IfFlatteningRule() }

    @Test
    fun `Rule properties`() = IfFlatteningRule().assertProperties()

    @Test
    fun `Empty or one statement in if statement`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                }
            }

            fun bar() {
                if (true) {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `Invert if with two statements`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                    baz()
                }
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 5, Messages[MSG])

    @Test
    fun `Do not invert when there is else`() =
        assertThatCode(
            """
            fun foo() {
                if (true) {
                    baz()
                    baz()
                } else {
                    baz()
                }
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
