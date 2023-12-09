package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.StringInterpolationRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class StringInterpolationRuleTest {
    private val assertThatCode = assertThatRule { StringInterpolationRule() }

    @Test
    fun `String templates`() =
        assertThatCode(
            """
            fun foo() {
                println("${'$'}name has ${'$'}{children.size} children")
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `String concatenation`() =
        assertThatCode(
            """
            fun foo() {
                println(name + " has " + children.size + " children")
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 13, Messages[MSG])

    @Test
    fun `Skip single plus operator`() =
        assertThatCode(
            """
            fun foo() {
                println(name + " has ")
            }
            """.trimIndent(),
        ).hasNoLintViolations()
}
