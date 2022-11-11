package com.hendraanggrian.lints.ktlint.kdoc

import com.hendraanggrian.lints.ktlint.kdoc.ParagraphContinuationFirstWordRule.Companion.ERROR_MESSAGE
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class ParagraphContinuationFirstWordRuleTest {
    private val assertCode = assertThatRule { ParagraphContinuationFirstWordRule() }

    @Test
    fun `Single line paragraph`() {
        assertCode(
            """
                /** `typesetting` */
                class MyClass

                /**
                 * `typesetting`
                 */
                class MyClass
            """.trimIndent()
        ).hasNoLintViolations()
    }

    @Test
    fun `First word of paragraph continuation is code`() {
        assertCode(
            """
                /**
                 * What is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and
                 * `typesetting` industry.
                 */
                class MyClass
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 3, ERROR_MESSAGE.format("code"))
        )
    }

    @Test
    fun `First word of paragraph continuation is link`() {
        assertCode(
            """
                /**
                 * What is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and
                 * [typesetting](http://some.url/) industry.
                 */
                class MyClass
            """.trimIndent()
        ).hasLintViolationsWithoutAutoCorrect(
            LintViolation(3, 3, ERROR_MESSAGE.format("link"))
        )
    }
}
