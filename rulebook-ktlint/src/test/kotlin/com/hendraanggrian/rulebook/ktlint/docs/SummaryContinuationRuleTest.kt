package com.hendraanggrian.rulebook.ktlint.docs

import com.hendraanggrian.rulebook.ktlint.Messages
import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class SummaryContinuationRuleTest {
    private val assertThatCode = assertThatRule { SummaryContinuationRule() }

    @Test
    fun `Single line paragraph`() = assertThatCode(
        """
        /** `typesetting` */
        class MyClass

        /**
         * `typesetting`
         */
        class MyClass
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `First word of paragraph continuation is code`() = assertThatCode(
        """
        /**
         * `What` is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and
         * `typesetting` industry.
         */
        class MyClass
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(3, 3, Messages[SummaryContinuationRule.MSG_CODE])

    @Test
    fun `First word of paragraph continuation is link`() = assertThatCode(
        """
        /**
         * [What](http://some.url/) is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing
         * and
         * [typesetting](http://some.url/) industry.
         */
        class MyClass
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(4, 3, Messages[SummaryContinuationRule.MSG_LINK])
}
