package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class StandardizeStringLiteralRuleTest {
    private val assertThatCode = assertThatRule { StandardizeStringLiteralRule() }

    @Test
    fun `Common color literals`() = assertThatCode(
        """
        val white = "#fff"
        val red = "#ff0000"
        val green = "#00ff00"
        val blue = "#0000ff"
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Uncommon color literals`() = assertThatCode(
        """
        val white = "#FFF"
        val red = "#FF0000"
        val green = "#00Ff00"
        val blue = "#0000fF"
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 13, Messages.get(StandardizeStringLiteralRule.MSG_COLOR, "#fff")),
        LintViolation(2, 11, Messages.get(StandardizeStringLiteralRule.MSG_COLOR, "#ff0000")),
        LintViolation(3, 13, Messages.get(StandardizeStringLiteralRule.MSG_COLOR, "#00ff00")),
        LintViolation(4, 12, Messages.get(StandardizeStringLiteralRule.MSG_COLOR, "#0000ff"))
    )

    @Test
    fun `Common encoding literals`() = assertThatCode(
        """
        val utf = "UTF-8"
        val ascii = "ASCII"
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Uncommon encoding literals`() = assertThatCode(
        """
        val utf = "utf-8"
        val ascii = "ascii"
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 11, Messages.get(StandardizeStringLiteralRule.MSG_ENCODING, "UTF-8")),
        LintViolation(2, 13, Messages.get(StandardizeStringLiteralRule.MSG_ENCODING, "ASCII"))
    )
}
