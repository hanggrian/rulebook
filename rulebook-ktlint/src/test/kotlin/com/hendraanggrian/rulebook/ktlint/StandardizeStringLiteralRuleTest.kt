package com.hendraanggrian.rulebook.ktlint

import com.pinterest.ktlint.test.KtLintAssertThat.Companion.assertThatRule
import com.pinterest.ktlint.test.LintViolation
import kotlin.test.Test

class StandardizeStringLiteralRuleTest {
    private val assertThatCode = assertThatRule { StandardizeStringLiteralRule() }

    @Test
    fun `Common literals`() = assertThatCode(
        """
        val encoding1 = "UTF-8"
        val encoding2 = "ASCII"
        val color1 = "#fff"
        val color2 = "#0000ff"
        """.trimIndent()
    ).hasNoLintViolations()

    @Test
    fun `Uncommon literals`() = assertThatCode(
        """
        val encoding1 = "utf-8"
        val encoding2 = "ascii"
        val color1 = "#FFF"
        val color2 = "#0000FF"
        val color3 = "#FFFFff"
        """.trimIndent()
    ).hasLintViolationsWithoutAutoCorrect(
        LintViolation(1, 17, Messages.get(StandardizeStringLiteralRule.MSG_ENCODING, "UTF-8")),
        LintViolation(2, 17, Messages.get(StandardizeStringLiteralRule.MSG_ENCODING, "ASCII")),
        LintViolation(3, 14, Messages.get(StandardizeStringLiteralRule.MSG_COLOR, "#fff")),
        LintViolation(4, 14, Messages.get(StandardizeStringLiteralRule.MSG_COLOR, "#0000ff")),
        LintViolation(5, 14, Messages.get(StandardizeStringLiteralRule.MSG_COLOR, "#ffffff"))
    )
}
