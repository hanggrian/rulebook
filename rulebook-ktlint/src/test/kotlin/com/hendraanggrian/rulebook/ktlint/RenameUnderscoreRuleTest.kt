package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import kotlin.test.Test

class RenameUnderscoreRuleTest {
    private val assertThatCode = assertThatRule { RenameUnderscoreRule() }

    @Test
    fun `Correct property`() = assertThatCode(
        """
        val myProperty = 1
        const val MY_PROPERTY = 2
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Property with underscore`() = assertThatCode(
        """
        val my_property = 1
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(1, 5, Messages.get(RenameUnderscoreRule.MSG, "myProperty"))

    @Test
    fun `Correct function`() = assertThatCode(
        """
        fun myFunction() {
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Function with underscore`() = assertThatCode(
        """
        fun my_function() {
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(1, 5, Messages.get(RenameUnderscoreRule.MSG, "myFunction"))

    @Test
    fun `Correct parameter`() = assertThatCode(
        """
        fun parameter(myParameter: Int) {
        }
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Parameter with underscore`() = assertThatCode(
        """
        fun parameter(my_parameter: Int) {
        }
        """.trimIndent()
    ).hasLintViolationWithoutAutoCorrect(
        1,
        15,
        Messages.get(RenameUnderscoreRule.MSG, "myParameter")
    )
}
