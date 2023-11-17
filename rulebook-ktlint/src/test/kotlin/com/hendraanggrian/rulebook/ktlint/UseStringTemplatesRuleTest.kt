package com.hendraanggrian.rulebook.ktlint

import com.hendraanggrian.rulebook.ktlint.UseStringTemplatesRule.Companion.MSG
import com.hendraanggrian.rulebook.ktlint.internals.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class UseStringTemplatesRuleTest {
    private val assertThatCode = assertThatRule { UseStringTemplatesRule() }

    @Test
    fun `String templates`() =
        assertThatCode(
            """
            fun print(name: String) {
                println("Hi ${'$'}name!");
            }
            """.trimIndent(),
        ).hasNoLintViolations()

    @Test
    fun `String concatenation`() =
        assertThatCode(
            """
            fun print(name: String) {
                println("Hi " + name + "!");
            }
            """.trimIndent(),
        ).hasLintViolationWithoutAutoCorrect(2, 13, Messages[MSG])
}
